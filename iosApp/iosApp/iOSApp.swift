import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        let koinDi = InitKoinKt.doInitAndGetKoin()
        let scheduler = koinDi.get(
            objCClass: BackgroundTaskSchedulerImpl.self
        ) as! BackgroundTaskScheduler

        scheduler.registerBackgroundTasks()
        scheduler.reScheduleBackgroundRefreshIfNeeded()

        requestNotificationPermission()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

func requestNotificationPermission() {
    UNUserNotificationCenter
        .current()
        .requestAuthorization(options: [.alert, .badge]) { (success, error) in
            if success {
                print("Permission granted.")
            } else if let error = error {
                print(error.localizedDescription)
            }
        }
}
