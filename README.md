# Immersive Firefox

Immersive Firefox is an unofficial personal fork of [Firefox for Android](https://github.com/mozilla-firefox/firefox), customized for immersive browsing on Android phones. Pixel 10 Pro is the primary test device; the arm64 APK also supports devices such as the Galaxy S24.

The current Android changes include:

- edge-to-edge content with a transparent navigation-bar background;
- a themed header and address-bar background that follow the current Fenix color scheme;
- a lightweight gradient scrim behind the status-bar icons while the top toolbar is collapsed;
- toolbar hiding while the user scrolls, including while a page is loading;
- a separate `org.mozilla.fenix.pixel` application id so the build can coexist with Firefox Nightly.

This is a personal customization and is not affiliated with or endorsed by Mozilla. It is not an official Firefox release. The project does not include or use Mozilla's official product artwork in this README.

## Build and install

Build and ADB installation instructions are in [Building-Pixel.md](mobile/android/fenix/docs/Building-Pixel.md).

The build uses the repository's existing Firefox/Fenix build system. The custom application id is intended for local testing and does not make the resulting APK an official Mozilla build.

## Upstream and development

The upstream source repository is [mozilla-firefox/firefox](https://github.com/mozilla-firefox/firefox). This fork follows upstream for the underlying Firefox source, but its Android UI behavior and local build configuration are maintained here. Report issues specific to this fork in this repository rather than Mozilla Bugzilla.

## License and trademarks

The existing [LICENSE](LICENSE) file is preserved. It directs you to [toolkit/content/license.html](toolkit/content/license.html), which contains the applicable copyright notices, the Mozilla Public License 2.0 text, and third-party license information. For files covered by the MPL, retain the required notices and follow the MPL terms when distributing modified source or binaries. Check individual file headers and the license page because not every component necessarily uses the same license.

The Firefox name, Mozilla names, logos, and other marks are trademarks. The license does not grant trademark rights. This project must not be presented as an official Mozilla or Firefox build; obtain the appropriate permission or rebrand a binary before public distribution.
