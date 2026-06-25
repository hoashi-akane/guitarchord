import SwiftUI
import shared
import GoogleMobileAds
import UserMessagingPlatform
import AppTrackingTransparency

@MainActor
class AdsManager: ObservableObject {
    @Published var showPrivacyEntry = false

    init() {
        if UMPConsentInformation.sharedInstance.canRequestAds {
            AdsState.shared.setCanShowAds(value: true)
        }
    }

    func requestConsent() {
        UMPConsentInformation.sharedInstance.requestConsentInfoUpdate(
            with: UMPRequestParameters()
        ) { [weak self] _ in
            guard let scene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
                  let rootVC = scene.windows.first?.rootViewController else { return }
            UMPConsentForm.loadAndPresentIfRequired(from: rootVC) { [weak self] _ in
                Task { @MainActor [weak self] in self?.updateStatus() }
            }
        }
    }

    func presentPrivacyOptions(from viewController: UIViewController) {
        UMPConsentForm.presentPrivacyOptionsForm(from: viewController) { [weak self] _ in
            Task { @MainActor [weak self] in self?.updatePrivacyEntryVisibility() }
        }
    }

    private func updateStatus() {
        if UMPConsentInformation.sharedInstance.canRequestAds {
            requestATT()
        }
        updatePrivacyEntryVisibility()
    }

    private func requestATT() {
        ATTrackingManager.requestTrackingAuthorization { status in
            Task { @MainActor in
                AdsState.shared.setCanShowAds(value: true)
            }
        }
    }

    private func updatePrivacyEntryVisibility() {
        showPrivacyEntry = UMPConsentInformation.sharedInstance.privacyOptionsRequirementStatus == .required
    }
}

struct ContentView: View {
    @StateObject private var adsManager = AdsManager()

    var body: some View {
        ComposeView(
            showPrivacyEntry: adsManager.showPrivacyEntry,
            onPrivacyOptionsClick: adsManager.showPrivacyEntry ? presentPrivacyOptions : nil,
            bannerViewFactory: makeBannerView
        )
        .ignoresSafeArea(.all)
        .onAppear { adsManager.requestConsent() }
    }

    private func makeBannerView() -> UIView {
        let banner = GADBannerView(adSize: GADAdSizeBanner)
        banner.adUnitID = "ca-app-pub-9272487388140178/9554939899"
        if let scene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
           let rootVC = scene.windows.first?.rootViewController {
            banner.rootViewController = rootVC
        }
        banner.load(GADRequest())
        return banner
    }

    private func presentPrivacyOptions() {
        guard let scene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
              let rootVC = scene.windows.first?.rootViewController else { return }
        adsManager.presentPrivacyOptions(from: rootVC)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    let showPrivacyEntry: Bool
    let onPrivacyOptionsClick: (() -> Void)?
    let bannerViewFactory: (() -> UIView)?

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            onPrivacyOptionsClick: showPrivacyEntry ? onPrivacyOptionsClick : nil,
            bannerViewFactory: bannerViewFactory
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}