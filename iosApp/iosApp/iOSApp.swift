import SwiftUI
import shared
import GoogleMobileAds

@main
struct iOSApp: App {
    init() {
        KoinHelperKt.doInitKoin()
        GADMobileAds.sharedInstance().start(completionHandler: nil)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}