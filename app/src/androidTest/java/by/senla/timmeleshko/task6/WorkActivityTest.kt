package by.senla.timmeleshko.task6

import android.app.Application
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.senla.timmeleshko.task6.model.DefaultServiceLocator
import by.senla.timmeleshko.task6.model.ServiceLocator
import by.senla.timmeleshko.task6.model.api.DataApi
import by.senla.timmeleshko.task6.view.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkActivityTest {

    private val workFactory = WorkFactory()

    @Before
    fun init() {
        val fakeApi = FakeDataApi()
        fakeApi.addWork(workFactory.createWork())
        val app = ApplicationProvider.getApplicationContext<Application>()
        ServiceLocator.swap(
            object : DefaultServiceLocator(app = app, useInMemoryDb = true) {
                override fun getDataApi(): DataApi {
                    return fakeApi
                }
            }
        )
    }

    @Test
    fun showSomeResults() {
        ActivityScenario.launch<MainActivity>(
            MainActivity.intentFor(
                context = ApplicationProvider.getApplicationContext()
            )
        )

        onView(withId(R.id.worksList)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(1, recyclerView.adapter?.itemCount)
        }
    }
}