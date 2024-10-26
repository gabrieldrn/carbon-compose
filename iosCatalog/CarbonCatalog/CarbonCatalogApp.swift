//
//  CarbonCatalogApp.swift
//  CarbonCatalog
//
//  Created by Gabriel Derrien on 2024-07-29.
//

import SwiftUI
import Catalog

@main
struct CarbonCatalogApp: App {

    init() {
        KoinHelperKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
