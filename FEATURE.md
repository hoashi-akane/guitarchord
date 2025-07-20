# FEATURE.md
今後実装したい新規機能について記載します。

# 機能一覧
- [ ] **単体テストの導入**: mockk, junit4を利用した単体テストの導入
- [ ] **コードの追加機能**: ユーザーが独自のギターコードを追加できるようにする。

以下 TBD ===
- [ ] **コードの編集機能**: ユーザーが追加したギターコードを編集できるようにする。
- [ ] **コードの検索機能**: ユーザーが特定のコードを検索できるようにする。
- [ ] **コードの削除機能**: ユーザーが追加したギターコードを削除できるようにする。
- [ ] **コードの共有機能**: ユーザーが自分のギターコードを他のユーザーと共有できるようにする。
===

# 機能の詳細
**単体テストの導入**
mockkライブラリ、AndroidJunit4を利用して、複雑なロジックに対するテストを追加できる状況にする。
Repositoryのメソッド、ViewModelのメソッドの単体テストを作成する。
ここでは、ロジックを変えない様に注意する。
変える場合は、別のPRで行う。

**コードの追加機能**
ユーザーが新しいギターコードを入力し、保存できるフォームを提供します。保存されたコードはアプリ内で表示されます。

***UIデザイン***
UIは既存の画面と同じ様なデザインとします。
違いとしては、一番上のフレット番号がある部分をユーザー入力を受け付ける数字のフィールドにします。
また、全ての指のボタンを設置して、そのボタンをドラック、ドロップすることで譜面上の位置に配置して、指番号・フレット番号を設定します。
開放限については最初は全て丸を表示し、上のロジックで対象の指番号にボタンが配置された場合、非表示にします。
保存ボタンを下部に設置し、保存が完了するとChordScreen.ktに戻ります。
ChordScreen.ktはカスタムコードが作成されている場合、"B"の横にカスタムボタンを表示し、タップするとカスタムしたコードの一覧が下のボタンリストに表示されます。

***遷移方法***
今の画面からの遷移はJetpackNavigationComposeライブラリを利用してください。
項目：カスタム　を選択するとコードの追加画面に遷移します。

***Dataレイヤー***
カスタムコードを保存するためにDBを利用します。
新しいCustomCodeRepositoryを作成し、以下のメソッドを実装します。
DBにはRoomを使用してください。
CustomChordのデータモデルもChord.ktを参考にして、TYPEはEnumではない形とします。
RoomのDB名は、`custom_chord_db`とします。
- `addCustomCode(code: CustomChord)`: 新しいカスタムコードを追加
- `getCustomCodes(): Flow<List<CustomChord>>`: 保存されたカスタムコードの一覧を取得
- `deleteCustomCode(code: CustomChord)`: カスタムコードを削除
- `updateCustomCode(code: CustomChord)`: カスタムコードを更新
- `getCustomCodeById(id: Long): Flow<CustomChord?>`: IDでカスタムコードを取得

# タスク一覧
- [ ] **単体テストの導入**
  - [ ] mockk, junit4のライブラリを追加 ブランチ名: `feature/add-unit-test-library`
  - [ ] ViewModelの単体テストを作成 ブランチ名: `feature/add-unit-test-for-viewmodel`
  - [ ] Repositoryの単体テストを作成 ブランチ名: `feature/add-unit-test-for-repository`
- [ ] **UIデザイン**
  - [ ] Navigation Composeライブラリの導入 ブランチ名: `feature/add-navigation-compose-library`
  - [ ] カスタムコード用の画面を作成 ブランチ名: `feature/add-custom-code-screen`
  - [ ] ドラッグ・ドロップで指ボタンを任意の指番号・フレットに配置する機能の実装 ブランチ名: `feature/add-drag-drop-functionality`
  - [ ] 保存ボタンの実装 ブランチ名: `feature/add-save-button`
  - [ ] Jetpack Navigation Composeを使用した画面遷移の実装 ブランチ名: `feature/add-navigation-functionality`
  - [ ] ChordScreen.ktにカスタムコードの一覧表示機能の実装 ブランチ名: `feature/add-custom-code-listing`
- [ ] **Dataレイヤー**
  - [ ] Roomライブラリの導入・セットアップ　ブランチ名: `feature/add-room-library`
  - [ ] CustomChordデータモデルの作成　ブランチ名: `feature/add-custom-chord-model`
  - [ ] CustomCodeRepositoryの実装　ブランチ名: `feature/add-custom-code-repository`
  - [ ] 機能の詳細で説明したメソッドの追加　ブランチ名: `feature/add-custom-code-repository-methods`
  - [ ] 単体テストの導入完了後に、CustomCodeRepositoryのテストを作成　ブランチ名: `feature/add-unit-test-for-custom-code-repository`


# タスク完了条件
- [ ] ビルドが通ること
- [ ] タスクの完了条件を満たしていること
- [ ] 作業ブランチをタスクで指定された名前で作成して作業してください。
- [ ] タスクの作業が完了したらPull Requestを作成してください。PRの単位は、タスクの単位とします。
- [ ] タスクは上からこなしてください。
- [ ] コンフリクトを防ぐためブランチは、前のタスクのブランチから派生させてください。

# 注意事項
- 機能の詳細で説明した内容を読んでタスクを実行してください。
- コードの追加機能は、既存のコードと同じアーキテクチャパターンに従って実装してください。
