package com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters

import android.app.Dialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

class CharactersDetailFragmentTest {

    @Test
    fun testSwipeBack_OnSwiped_CallsDialogCancel() {
        // Mock dialog
        val mockDialog: Dialog = mock()

        val swipeBack = CharactersDetailFragment.swipeBack(mockDialog)

        val mockRecyclerView: RecyclerView = mock()
        val mockViewHolder: RecyclerView.ViewHolder = mock()

        swipeBack.onSwiped(mockViewHolder, ItemTouchHelper.UP)

        // Verify that the dialog's cancel method is called
        verify(mockDialog).cancel()
    }

    @Test
    fun testSwipeBack_OnSwiped_CallsDialogCancelWithDifferentDirection() {
        // Mock dialog
        val mockDialog: Dialog = mock()

        val swipeBack = CharactersDetailFragment.swipeBack(mockDialog)

        val mockRecyclerView: RecyclerView = mock()
        val mockViewHolder: RecyclerView.ViewHolder = mock()

        swipeBack.onSwiped(mockViewHolder, ItemTouchHelper.DOWN)

        // Verify that the dialog's cancel method is called
        verify(mockDialog).cancel()
    }

}
