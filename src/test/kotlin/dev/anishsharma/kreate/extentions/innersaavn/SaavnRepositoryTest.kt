package dev.anishsharma.kreate.extentions.innersaavn

import kotlin.test.Test
import kotlin.test.assertNotNull

class SaavnRepositoryTest {
  @Test
  fun constructRepo() {
    val api = SaavnApi(baseUrl = "https://saavn.dev/")
    val repo = SaavnRepository(api)
    assertNotNull(repo)
  }
}