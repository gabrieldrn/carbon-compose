//
//  ContentView.swift
//  CarbonCatalog
//
//  Created by Gabriel Derrien on 2024-07-29.
//

import SwiftUI
import Carbon

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all, edges: .bottom)
    }
}

#Preview {
    ContentView()
}
