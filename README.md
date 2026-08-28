![Immersive Firefox](./docs/readme/readme-banner.svg)

# Immersive Firefox

This repository is an unofficial personal fork of [Firefox for Android](https://github.com/mozilla-firefox/firefox), customized for immersive browsing on the Pixel 10 Pro.

The current Android changes include:

- edge-to-edge content with a transparent navigation-bar background;
- synchronized scrolling of the browser toolbar and the top background layer;
- toolbar hiding while the user scrolls, including while a page is loading;
- a separate `org.mozilla.fenix.pixel` application id so the build can coexist with Firefox Nightly.

This is a personal customization and is not affiliated with or endorsed by Mozilla. It is not an official Firefox release.

## Build and install

Pixel-specific build and ADB installation instructions are in [Building-Pixel.md](mobile/android/fenix/docs/Building-Pixel.md).

The build uses the repository's existing Firefox/Fenix build system. The custom application id is intended for local testing and does not make the resulting APK an official Mozilla build.

## Upstream

The upstream source repository is [mozilla-firefox/firefox](https://github.com/mozilla-firefox/firefox). Changes in this fork should be reviewed against upstream documentation and licensing notices before redistribution.

## License and trademarks

The existing [LICENSE](LICENSE) file is preserved. It directs you to [toolkit/content/license.html](toolkit/content/license.html), which contains the applicable copyright notices, the Mozilla Public License 2.0 text, and third-party license information. For files covered by the MPL, retain the required notices and follow the MPL terms when distributing modified source or binaries. Check individual file headers and the license page because not every component necessarily uses the same license.

The Firefox name, Mozilla names, logos, and other marks are trademarks. The license does not grant trademark rights. This project must not be presented as an official Mozilla or Firefox build; obtain the appropriate permission or rebrand a binary before public distribution.

---

[Firefox](https://firefox.com/) is a fast, reliable and private web browser from the non-profit [Mozilla organization](https://mozilla.org/).

### Contributing

To learn how to contribute to Firefox read the [Firefox Contributors' Quick Reference document](https://firefox-source-docs.mozilla.org/contributing/contribution_quickref.html).

We use [bugzilla.mozilla.org](https://bugzilla.mozilla.org/) as our issue tracker, please file bugs there.

### Resources

* [Firefox Source Docs](https://firefox-source-docs.mozilla.org/) is our primary documentation repository
* Nightly development builds can be downloaded from [Firefox Nightly page](https://www.mozilla.org/firefox/channel/desktop/#nightly)

If you have a question about developing Firefox, and can't find the solution
on [Firefox Source Docs](https://firefox-source-docs.mozilla.org/), you can try asking your question on Matrix at
chat.mozilla.org in the [Introduction channel](https://chat.mozilla.org/#/room/#introduction:mozilla.org).
