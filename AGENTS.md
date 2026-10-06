# 開発・リリースの進め方

- 誰でも使えるOSSのプラグインとして作る。特定のサーバー（spa77-smpなど）専用の文面・設定・依存を入れない。サーバー固有の設定は使う側のリポジトリ（`../spsmc-infra/plugins/DiscordSRVJa/`）に置く。
- 翻訳はMojang公式の言語ファイルを実行時に取得して使う。言語ファイル（`ja_jp.json`など）をリポジトリやJARに入れない。
- 作業前に`git status --short`を確認し、既存の変更を上書き・削除しない。
- 変更したら `mvn -B package`（ユニットテスト込み）を通す。ローカルにJavaがないときは `docker run --rm -v "$PWD":/src -v "$HOME/.m2":/root/.m2 -w /src maven:3.9-eclipse-temurin-25 mvn -B package`。
- 起動確認は、Paperと DiscordSRV を入れたサーバーで、ログに`subscribed (2 methods)`と`Loaded ja_jp for Minecraft <版>`が出ることを見る。
- Discordへ実際に翻訳された文が届いたかは、DiscordSRVをつないだサーバーでしか確かめられない。確かめたかどうかを報告する。
