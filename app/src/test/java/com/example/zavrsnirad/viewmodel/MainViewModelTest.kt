package com.example.zavrsnirad.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.zavrsnirad.model.ProductModel
import com.example.zavrsnirad.repository.IProductRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var mockRepository: IProductRepository

    @Before
    fun setUp() {
        mockRepository = mock()
    }

    @Test
    fun ucitavanje_proizvoda_azurira_live_data() {
        val dummyProducts = listOf(
            ProductModel(1, "Test Proizvod 1", 100, "Info1", 0),
            ProductModel(2, "Test Proizvod 2", 200, "Info2", 0)
        )
        whenever(mockRepository.getProductList()).thenReturn(dummyProducts)

        viewModel = MainViewModel(mockRepository)

        val result = viewModel.productList.value

        assertEquals(2, result?.size)
        assertEquals("Test Proizvod 1", result?.get(0)?.title)
        assertEquals(200, result?.get(1)?.price)
    }
}