import Testing
import Roff

@Suite("Roff Swift Export Tests")
struct RoffExportTests {
    @Test("Swift module loads cleanly")
    func testSwiftModuleLoads() {
        #expect(Bool(true), "Roff swift module imported cleanly")
    }
}
