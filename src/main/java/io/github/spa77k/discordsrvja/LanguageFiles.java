package io.github.spa77k.discordsrvja;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Map;

/**
 * Gets a Minecraft language file from Mojang's official servers and keeps a copy on disk.
 */
final class LanguageFiles {
    private static final String MANIFEST = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    private static final String RESOURCES = "https://resources.download.minecraft.net/";

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
    private final Path directory;

    LanguageFiles(Path directory) {
        this.directory = directory;
    }

    /** Returns the language file for this Minecraft version, downloading it if there is no copy yet. */
    Map<String, String> load(String minecraftVersion, String language) throws IOException, InterruptedException {
        Path file = directory.resolve(minecraftVersion).resolve(language + ".json");
        if (!Files.isRegularFile(file)) {
            byte[] downloaded = download(minecraftVersion, language);
            Files.createDirectories(file.getParent());
            Path temp = file.resolveSibling(file.getFileName() + ".tmp");
            Files.write(temp, downloaded);
            Files.move(temp, file, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        return new Gson().fromJson(Files.readString(file, StandardCharsets.UTF_8),
                new TypeToken<Map<String, String>>() { }.getType());
    }

    private byte[] download(String minecraftVersion, String language) throws IOException, InterruptedException {
        JsonObject manifest = getJson(MANIFEST);
        String versionUrl = null;
        for (var element : manifest.getAsJsonArray("versions")) {
            JsonObject version = element.getAsJsonObject();
            if (version.get("id").getAsString().equals(minecraftVersion)) {
                versionUrl = version.get("url").getAsString();
                break;
            }
        }
        if (versionUrl == null) {
            throw new IOException("Minecraft " + minecraftVersion + " is not in Mojang's version list");
        }
        String indexUrl = getJson(versionUrl).getAsJsonObject("assetIndex").get("url").getAsString();
        JsonObject object = getJson(indexUrl).getAsJsonObject("objects")
                .getAsJsonObject("minecraft/lang/" + language + ".json");
        if (object == null) {
            throw new IOException("Language " + language + " does not exist for Minecraft " + minecraftVersion);
        }
        String hash = object.get("hash").getAsString();
        byte[] body = get(RESOURCES + hash.substring(0, 2) + "/" + hash);
        if (!sha1(body).equals(hash)) {
            throw new IOException("Downloaded " + language + ".json does not match its SHA-1");
        }
        return body;
    }

    private JsonObject getJson(String url) throws IOException, InterruptedException {
        return JsonParser.parseString(new String(get(url), StandardCharsets.UTF_8)).getAsJsonObject();
    }

    private byte[] get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(30)).GET().build();
        HttpResponse<byte[]> response = http.send(request, HttpResponse.BodyHandlers.ofByteArray());
        if (response.statusCode() != 200) {
            throw new IOException("HTTP " + response.statusCode() + " from " + url);
        }
        return response.body();
    }

    private static String sha1(byte[] data) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-1").digest(data));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
