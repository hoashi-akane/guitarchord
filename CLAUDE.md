# CLAUDE.md
Caludeは日本語で応答してください。
このファイルは、このリポジトリで作業する際のClaude Code (claude.ai/code) への指針を提供します。

## プロジェクト概要
このアプリケーションは、Jetpack Composeを使用してKotlinで書かれたAndroidギターコードアプリです。左利き（レフティー）モードと多言語対応（英語・日本語）をサポートしたギターコードの運指図を表示します。

## よく使う開発コマンド

### ビルド
```bash
# デバッグAPKをビルド
./gradlew assembleDebug

# リリースAPKをビルド  
./gradlew assembleRelease

# Play Store用のApp Bundleをビルド
./gradlew bundleRelease

# クリーンして再ビルド
./gradlew clean build
```

### テスト
```bash
# ユニットテストを実行
./gradlew test

# 接続されたデバイスでインストルメンテッドテストを実行
./gradlew connectedAndroidTest
```

### リントとコード品質
```bash
# Android lintを実行
./gradlew lint

# Kotlinコードフォーマット用のktlintを実行
java -jar ktlint-compose-0.4.22-all.jar
```

### インストール
```bash
# デバッグビルドをデバイスにインストール
./gradlew installDebug
```

## アーキテクチャ概要

### MVVMアーキテクチャパターン
このアプリはRepositoryパターンを含むMVVM（Model-View-ViewModel）アーキテクチャに従っています：

1. **Viewレイヤー** (Compose UI)
   - `ChordScreen.kt`: メインのコード表示画面
   - `ChordScreenTopAppBar.kt`: 設定メニュー付きアプリバー
   - Canvasベースのコード図描画

2. **ViewModelレイヤー**
   - `ChordScreenViewModel`: UIの状態とビジネスロジックを管理
   - Hilt依存性注入を使用（`@HiltViewModel`）
   - リアクティブな状態管理のためのStateFlow

3. **Repositoryレイヤー**
   - `SettingsRepository`: 設定操作のインターフェース
   - `SettingsRepositoryImpl`: 永続化のためのDataStore実装
   - データ操作のクリーンな抽象化

4. **依存性注入**
   - 依存関係管理のためのHilt/Dagger
   - `AppModule`: シングルトン依存関係を提供
   - Activities/Fragmentsの`@AndroidEntryPoint`

### 主要なデータモデル

1. **コードシステム**
   - `Chord`: アルファベット、シャープフラグ、コードタイプを持つメインエンティティ
   - `ChordType`: 指のポジションと開放弦情報を含む
   - `FingerPosition`: フレットと弦の範囲を表現
   - ノート（A-G）で整理された事前定義済みコードデータ

2. **設定**
   - DataStoreで永続化される左利きモードトグル
   - StateFlowを介したリアルタイムUI更新

### UI実装の詳細

1. **コードCanvas描画**
   - コード図のためのカスタムCanvas API実装
   - バレーコードと複数弦ポジションをサポート
   - レフティーモード用の水平反転変換
   - 動的なサイズと位置の計算

2. **コンポーネント構造**
   - アルファベット選択ボタン（C-B）
   - フローレイアウトのコードタイプ選択
   - トップアプリバーの設定メニュー
   - シャープボタン（現在無効）

3. **国際化**
   - 英語と日本語のサポート
   - 日本語（`ja`）のロケールフィルタリング
   - ロケール別の指の命名（数字vs漢字）

### 技術スタック
- **Kotlin**: 2.1.20
- **Compose BOM**: 2025.03.01
- **Target SDK**: 35 (Android 15)
- **Min SDK**: 26 (Android 8.0)
- **Hilt**: 2.52
- **Firebase**: AnalyticsとCrashlytics
- **DataStore**: 設定の保存用
- **Material 3**: ダイナミックカラーサポート付き

### 開発に関する注意事項

1. **現在の制限事項**
   - シャープ（#）コードのバリエーションが未実装
   - 存在しないコードに対するエラーハンドリングが限定的
   - テストカバレッジが最小限

2. **計画されている機能**（コードコメントより）
   - 存在しないコードのエラー表示
   - 表示カスタマイズオプション
   - 追加のコードタイプ（6th、augmented、diminished）

3. **パフォーマンスの考慮事項**
   - 適切なstate hoistingによる効率的な再コンポジション
   - コード図のために最適化されたCanvas描画
   - 単方向データフローパターン

4. **ファイル構成**
   - `/app/src/main/java/jp/ahoashi/guitarchord/`: メインソースコード
   - `/domain/`: 複雑なビジネスロジック *UseCaseなど
   - `/entity/`: アプリ内部で持つエンティティ
   - `/core/`: RepositoryのIFやエンティティ
   - `/data/`: Repository実装
   - `/data/module/`: 依存性注入モジュール