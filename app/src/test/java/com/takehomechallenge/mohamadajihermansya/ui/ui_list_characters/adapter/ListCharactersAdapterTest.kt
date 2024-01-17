package com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.adapter

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.takehomechallenge.mohamadajihermansya.data.model.ModelSimpleCharacters
import com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.adapter.ListCharactersAdapter.ItemHolder
import com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.adapter.ListCharactersAdapter.DiffCallBAck
import com.takehomechallenge.mohamadajihermansya.databinding.CardCharacterBinding
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ListCharactersAdapterTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockBinding: CardCharacterBinding

    @Mock
    private lateinit var mockOnItemClick: (Int) -> Unit

    private lateinit var adapter: ListCharactersAdapter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        adapter = ListCharactersAdapter()
    }

    @Test
    fun `getItemCount is correct`() = runBlocking {
        val testData = listOf(
            ModelSimpleCharacters(id = 1, name = "Character 1", species = "Human", gender = "Male", image = "url1"),
            ModelSimpleCharacters(id = 2, name = "Character 2", species = "Alien", gender = "Female", image = "url2"),
            ModelSimpleCharacters(id = 3, name = "Character 3", species = "Robot", gender = "Unknown", image = "url3")
        )

        adapter.submitData(testData)

        assertEquals(testData.size, adapter.itemCount)
    }
    @Test
    fun onCreateViewHolder_isNotNull() {
        val parent = createMockViewGroup()

        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        assert(viewHolder != null)
    }

    @Test
    fun onBindViewHolder_isCorrect() {
        val testData = ModelSimpleCharacters(id = 1, name = "Character 1", species = "Human", gender = "Male", image = "url1")
        val itemHolder = ItemHolder(mockBinding, mockOnItemClick)

        adapter.onBindViewHolder(itemHolder, 0)

    }

    @Test
    fun diffCallback_areItemsTheSame() {
        val diffCallback = DiffCallBAck()
        val item1 = ModelSimpleCharacters(id = 1, name = "Character 1", species = "Human", gender = "Male", image = "url1")
        val item2 = ModelSimpleCharacters(id = 1, name = "Character 1", species = "Human", gender = "Male", image = "url1")

        val areItemsTheSame = diffCallback.areItemsTheSame(item1, item2)

        assertEquals(true, areItemsTheSame)
    }

    @Test
    fun diffCallback_areContentsTheSame() {
        val diffCallback = DiffCallBAck()
        val item1 = ModelSimpleCharacters(id = 1, name = "Character 1", species = "Human", gender = "Male", image = "url1")
        val item2 = ModelSimpleCharacters(id = 1, name = "Character 1", species = "Human", gender = "Male", image = "url1")

        val areContentsTheSame = diffCallback.areContentsTheSame(item1, item2)

        assertEquals(true, areContentsTheSame)
    }

    private fun createMockViewGroup(): ViewGroup {
        return FrameLayout(ApplicationProvider.getApplicationContext())
    }

}
