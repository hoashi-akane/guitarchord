import SwiftUI
import shared
import GoogleMobileAds
import Firebase

@main
struct iOSApp: App {
    init() {
        FirebaseApp.configure()
        KoinHelperKt.doInitKoin()
        GADMobileAds.sharedInstance().start(completionHandler: nil)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}