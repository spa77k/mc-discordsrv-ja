# DiscordSRVJa

[日本語](README.ja.md)

A Paper plugin that translates the death messages and advancement titles that [DiscordSRV](https://github.com/DiscordSRV/DiscordSRV) sends to Discord.

Minecraft servers only know English, so DiscordSRV posts `Steve was slain by Zombie` and `Sweet Dreams` even when every player uses Japanese. With this plugin they become `Steveはゾンビに殺害された` and `良い夢見てね` — the same wording players see in game.

- Uses Minecraft's official language file for the running version, downloaded from Mojang's servers on first start.
- Works with any language Minecraft has (`ja_jp`, `ko_kr`, `zh_cn`, `de_de`, ...).
- Only changes what DiscordSRV sends to Discord. In-game chat is not touched.

## Requirements

- Paper 26.2 or newer (tested on Paper 26.2 and 26.3)
- DiscordSRV 1.30 or newer
- Internet access from the server on first start (to download the language file)

## Install

1. Download `discordsrv-ja-<version>.jar` from [Releases](https://github.com/spa77k/mc-discordsrv-ja/releases).
2. Put it in your server's `plugins/` folder next to DiscordSRV.
3. Restart the server. The log shows `Loaded ja_jp for Minecraft <version>`.

Your DiscordSRV message templates (`messages.yml`) still decide the layout; this plugin only fills `%deathmessage%` and the advancement name in the chosen language.

## Settings

`plugins/DiscordSRVJa/config.yml`:

| Key | Default | What it does |
| --- | --- | --- |
| `language` | `ja_jp` | Minecraft language file to use |
| `death-messages` | `true` | Translate death messages |
| `advancements` | `true` | Translate advancement titles |

The downloaded file is saved in `plugins/DiscordSRVJa/lang/<Minecraft version>/`. After a Minecraft update, the file for the new version is downloaded on the next start. If the download fails, messages are sent in English as before.

## Build

```bash
mvn -B package
```

## License

MIT
