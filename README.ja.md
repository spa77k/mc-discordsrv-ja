# DiscordSRVJa

[English](README.md)

[DiscordSRV](https://github.com/DiscordSRV/DiscordSRV)がDiscordへ送る死亡メッセージと進捗名を、日本語などに翻訳するPaperプラグインです。

Minecraftのサーバーは英語しか持っていないため、DiscordSRVは`Steve was slain by Zombie`や`Sweet Dreams`のように英語で送ります。このプラグインを入れると、ゲーム内と同じ言い回しの`Steveはゾンビに殺害された`、`良い夢見てね`になります。

- 動いているMinecraftの版の公式言語ファイルを使います。初回起動時にMojangの配信サーバーから取得します。
- Minecraftにある言語ならどれでも使えます（`ja_jp`、`ko_kr`、`zh_cn`、`de_de`など）。
- 変えるのはDiscordへ送る文だけです。ゲーム内のチャットは変わりません。

## 必要なもの

- Paper 26.2 以降（Paper 26.2・26.3で動作確認）
- DiscordSRV 1.30 以降
- 初回起動時にサーバーからインターネットへ接続できること（言語ファイルの取得）

## 入れ方

1. [Releases](https://github.com/spa77k/mc-discordsrv-ja/releases)から`discordsrv-ja-<版>.jar`をダウンロードします。
2. DiscordSRVと同じ`plugins/`フォルダーに入れます。
3. サーバーを再起動します。ログに`Loaded ja_jp for Minecraft <版>`と出れば使えます。

Discordに出る文の形はこれまでどおりDiscordSRVの`messages.yml`で決まります。このプラグインは`%deathmessage%`と進捗名の中身を選んだ言語にするだけです。

## 設定

`plugins/DiscordSRVJa/config.yml`:

| キー | 既定値 | 内容 |
| --- | --- | --- |
| `language` | `ja_jp` | 使うMinecraftの言語ファイル |
| `death-messages` | `true` | 死亡メッセージを翻訳する |
| `advancements` | `true` | 進捗名を翻訳する |

取得したファイルは`plugins/DiscordSRVJa/lang/<Minecraftの版>/`に保存します。Minecraftを更新すると、次の起動時に新しい版のファイルを取得します。取得に失敗したときは、これまでどおり英語で送ります。

## ビルド

```bash
mvn -B package
```

## ライセンス

MIT
