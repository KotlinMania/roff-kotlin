# roff-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Froff--kotlin-blue.svg)](https://github.com/KotlinMania/roff-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/roff-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/roff-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/roff-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/roff-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`rust-cli/roff-rs`](https://github.com/rust-cli/roff-rs).

**Original Project:** This port is based on [`rust-cli/roff-rs`](https://github.com/rust-cli/roff-rs). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `rust-cli/roff-rs`

> The text below is reproduced and lightly edited from [`https://github.com/rust-cli/roff-rs`](https://github.com/rust-cli/roff-rs). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## roff-rs

[![Documentation](https://img.shields.io/badge/docs-master-blue.svg)][Documentation]
![License](https://img.shields.io/crates/l/roff.svg)
[![crates.io](https://img.shields.io/crates/v/roff.svg)][Crates.io]

[Crates.io]: https://crates.io/crates/roff
[Documentation]: https://docs.rs/roff/

[Roff](http://man7.org/linux/man-pages/man7/roff.7.html) generation library.

## Examples

```rust
use roff::{bold, italic, roman, Roff};

fn main() {
    let page = Roff::new()
        .control("TH", ["CORRUPT", "1"])
        .control("SH", ["NAME"])
        .text([roman("corrupt - modify files by randomly changing bits")])
        .control("SH", ["SYNOPSIS"])
        .text([bold("corrupt"), roman(" ["), bold("-n"), roman(" "), italic("BITS"), roman("] ["),
               bold("--bits"), roman(" "), italic("BITS"), roman("] "), italic("FILE"), roman("..."),
        ])
        .control("SH", ["DESCRIPTION"])
        .text([bold("corrupt"), roman(" modifies files by toggling a randomly chosen bit.")])
        .control("SH", ["OPTIONS"])
        .control("TP", [])
        .text([bold("-n"), roman(", "), bold("--bits"), roman("="), italic("BITS")])
        .text([roman("Set the number of bits to modify. Default is one bit.")])
        .render();
    print!("{}", page);
}
```

Which outputs:
```troff
.ie \n(.g .ds Aq \(aq
.el .ds Aq '
.TH CORRUPT 1
.SH NAME
corrupt \- modify files by randomly changing bits
.SH SYNOPSIS
\fBcorrupt\fR [\fB\-n\fR \fIBITS\fR] [\fB\-\-bits\fR \fIBITS\fR] \fIFILE\fR...
.SH DESCRIPTION
\fBcorrupt\fR modifies files by toggling a randomly chosen bit.
.SH OPTIONS
.TP
\fB\-n\fR, \fB\-\-bits\fR=\fIBITS\fR
Set the number of bits to modify. Default is one bit.
```

Which will be shown by the `man(1)` command as:

```txt
CORRUPT(1)                 General Commands Manual                CORRUPT(1)

NAME
       corrupt - modify files by randomly changing bits

SYNOPSIS
       corrupt [-n BITS] [--bits BITS] FILE...

DESCRIPTION
       corrupt modifies files by toggling a randomly chosen bit.

OPTIONS
       -n, --bits=BITS
              Set the number of bits to modify. Default is one bit.

                                                                  CORRUPT(1)
```

## License

Licensed under either of

* Apache License, Version 2.0, ([LICENSE-APACHE](https://github.com/rust-cli/roff-rs/blob/HEAD/LICENSE-APACHE) or <https://www.apache.org/licenses/LICENSE-2.0>)
* MIT license ([LICENSE-MIT](https://github.com/rust-cli/roff-rs/blob/HEAD/LICENSE-MIT) or <https://opensource.org/license/mit>)

at your option.

### Contribution

Unless you explicitly state otherwise, any contribution intentionally
submitted for inclusion in the work by you, as defined in the Apache-2.0
license, shall be dual-licensed as above, without any additional terms or
conditions.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:roff-kotlin:0.1.4")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`rust-cli/roff-rs`](https://github.com/rust-cli/roff-rs). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the roff-rs authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`rust-cli/roff-rs`](https://github.com/rust-cli/roff-rs) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
