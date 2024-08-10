//
//  ContentView.swift
//  CarbonCatalog
//
//  Created by Gabriel Derrien on 2024-07-29.
//

import SwiftUI
import Catalog

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController(onOpenLink: { link in
            guard let url = URL(string: link) else { return }
            UIApplication.shared.open(url)
        })
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all, edges: .vertical)
    }
}

#Preview {
    ContentView()
}
