# DiscordSRVJa

[日本語](README.ja.md)

A Paper plugin that translates death messages and advancement titles sent to Discord by [DiscordSRV](https://github.com/DiscordSRV/DiscordSRV) using official Mojang language files.

Minecraft servers only store messages in English, so DiscordSRV typically posts notifications such as `Steve was slain by Zombie` and `Sweet Dreams` even if players use another language. With DiscordSRVJa, Discord notifications use the same localized wording players see in game (e.g., `Steveはゾンビに殺害された` and `良い夢見てね`).

- Uses official Minecraft language files for the running version, downloaded automatically from Mojang's servers on first launch.
- Supports any language available in Minecraft (`ja_jp`, `ko_kr`, `zh_cn`, `de_de`, etc.).
- Only affects messages sent to Discord by DiscordSRV; in-game chat and messages remain untouched.

## Requirements

- Paper 26.2 or newer (tested on Paper 26.2 and 26.3)
- DiscordSRV 1.30 or newer
- Internet access on first launch (to download the language file)

## Installation

1. Download `discordsrv-ja-1.0.1.jar` from [Releases](https://github.com/spa77k/mc-discordsrv-ja/releases).
2. Place it into your server's `plugins/` directory alongside DiscordSRV.
3. Restart the server. Verify that the console logs `Loaded ja_jp for Minecraft <version>`.

Message layout and formatting are still controlled by DiscordSRV's `messages.yml`. DiscordSRVJa only replaces the contents of `%deathmessage%` and advancement names with the translated text.

## Commands & Permissions

This plugin provides no commands or permissions. Everything is managed through the configuration file.

## Configuration

Configuration file: `plugins/DiscordSRVJa/config.yml`

| Key | Default | Description |
| --- | --- | --- |
| `language` | `ja_jp` | Minecraft language code to use |
| `death-messages` | `true` | Whether to translate death messages sent to Discord |
| `advancements` | `true` | Whether to translate advancement titles sent to Discord |

Downloaded language files are cached at `plugins/DiscordSRVJa/lang/<Minecraft version>/<language>.json`. When Minecraft is upgraded, the file for the new version is automatically downloaded on the next server start. If the download fails, messages are sent untranslated (in English).

## License

MIT
