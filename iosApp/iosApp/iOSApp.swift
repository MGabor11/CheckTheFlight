import SwiftUI

@main
struct iOSApp: App {
    
    init() {
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
