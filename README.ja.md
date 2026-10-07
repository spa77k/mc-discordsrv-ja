# DiscordSRVJa

[English](README.md)

[DiscordSRV](https://github.com/DiscordSRV/DiscordSRV) が Discord へ送信する死亡メッセージと進捗名を、Mojang 公式の言語ファイルを使って指定した言語に翻訳する Paper プラグインです。

Minecraft サーバーは英語の文面しか保持していないため、DiscordSRV は通常 `Steve was slain by Zombie` や `Sweet Dreams` のように英語で Discord に投稿します。DiscordSRVJa を導入すると、プレイヤーがゲーム内で見ている表示と同じ言い回し（例: `Steveはゾンビに殺害された`、`良い夢見てね`）で Discord に通知されるようになります。

- 実行中の Minecraft バージョンに対応する公式言語ファイルを、初回起動時に Mojang の配信サーバーから自動取得して使用します。
- Minecraft が対応している任意の言語（`ja_jp`、`ko_kr`、`zh_cn`、`de_de` など）を利用できます。
- 変更されるのは DiscordSRV が Discord へ送信する文面のみです。ゲーム内のチャットやメッセージには影響しません。

## 動作環境

- Paper 26.2 以降（Paper 26.2・26.3 で動作確認済み）
- DiscordSRV 1.30 以降
- 初回起動時にサーバーからインターネットへ接続できること（言語ファイル取得のため）

## 導入方法

1. [Releases](https://github.com/spa77k/mc-discordsrv-ja/releases) から `discordsrv-ja-1.0.1.jar` をダウンロードします。
2. サーバーの `plugins/` フォルダーに配置します（DiscordSRV と同じ階層）。
3. サーバーを再起動します。起動ログに `Loaded ja_jp for Minecraft <バージョン>` と表示されれば完了です。

Discord に送信されるメッセージ全体のレイアウトは DiscordSRV の `messages.yml` で設定されます。本プラグインは `%deathmessage%` や進捗名の中身を指定した言語に置き換えます。

## コマンド・権限

追加されるコマンドや権限はありません。設定ファイルのみで動作します。

## 設定

設定ファイル: `plugins/DiscordSRVJa/config.yml`

| 設定項目 | 既定値 | 説明 |
| --- | --- | --- |
| `language` | `ja_jp` | 使用する Minecraft の言語コード |
| `death-messages` | `true` | Discord への死亡メッセージを翻訳する |
| `advancements` | `true` | Discord への進捗名を翻訳する |

取得した言語ファイルは `plugins/DiscordSRVJa/lang/<Minecraftバージョン>/<言語名>.json` に保存されます。Minecraft のバージョンを更新すると、次回起動時に新しいバージョンの言語ファイルを自動取得します。取得に失敗した場合は、英語（未翻訳）のままメッセージが送信されます。

## ライセンス

MIT
