package ch.kra.mycellar.ui.viewmodels

import ch.kra.mycellar.MainDispatcherRule
import ch.kra.mycellar.repositories.FakeWineRepository
import ch.kra.mycellar.core.DispatcherProvider
import ch.kra.mycellar.util.TestDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WineDetailViewModelTest {
    lateinit var wineDetailViewModel: WineDetailViewModel
    lateinit var fakeWineRepository: FakeWineRepository
    lateinit var testDispatchers: DispatcherProvider

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        testDispatchers = TestDispatchers()
        fakeWineRepository = FakeWineRepository()
        wineDetailViewModel = WineDetailViewModel(fakeWineRepository, testDispatchers)
    }

    @Test
    fun `Test insert`() = runTest {
        wineDetailViewModel.addWine(
            wineName = "test",
            quantity = "5",
            wineType = 1,
            offeredBy = ""
        )
        advanceUntilIdle()
        assertEquals("The wine name should be test", "test", fakeWineRepository.currentWine?.wineName)
    }
}