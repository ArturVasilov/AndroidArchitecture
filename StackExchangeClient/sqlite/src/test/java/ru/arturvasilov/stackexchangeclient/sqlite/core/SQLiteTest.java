package ru.arturvasilov.stackexchangeclient.sqlite.core;

import android.app.Application;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.sqlite.BuildConfig;
import ru.arturvasilov.stackexchangeclient.sqlite.SQLite;
import ru.arturvasilov.stackexchangeclient.sqlite.table.Table;
import ru.arturvasilov.stackexchangeclient.sqlite.testutils.SQLiteEnv;
import ru.arturvasilov.stackexchangeclient.sqlite.testutils.TestContentClass;
import ru.arturvasilov.stackexchangeclient.sqlite.testutils.TestTable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class SQLiteTest {

    private SQLiteContentProvider mProvider;

    @Before
    public void setUp() throws Exception {
        List<Table> tables = new ArrayList<Table>() {{
            add(TestTable.TABLE);
        }};
        mProvider = SQLiteEnv.registerProvider(tables);

        Application application = RuntimeEnvironment.application;
        application.onCreate();
        SQLite.initialize(application);

        SQLite.get().delete(TestTable.TABLE).execute();
    }

    @Test
    public void testInsertElement() throws Exception {
        TestContentClass test = new TestContentClass(5, "aaaa");
        SQLite.get().insert(TestTable.TABLE).insert(test);
        TestContentClass saved = SQLite.get().query(TestTable.TABLE).object().execute();
        assertEquals(test, saved);
    }

    @Test
    public void testInsertMultiple() throws Exception {
        List<TestContentClass> elements = new ArrayList<>();
        elements.add(new TestContentClass(1, "a"));
        elements.add(new TestContentClass(2, "ab"));
        elements.add(new TestContentClass(3, "abc"));
        elements.add(new TestContentClass(4, "abcd"));
        elements.add(new TestContentClass(5, "abcde"));
        SQLite.get().insert(TestTable.TABLE).insert(elements);

        int savedSize = SQLite.get().query(TestTable.TABLE).all().execute().size();
        assertEquals(elements.size(), savedSize);
    }

    @Test
    public void testInsertReplacedPrimaryKey() throws Exception {
        List<TestContentClass> elements = new ArrayList<>();
        elements.add(new TestContentClass(11, "a"));
        elements.add(new TestContentClass(12, "ab"));
        SQLite.get().insert(TestTable.TABLE).insert(elements);

        SQLite.get().insert(TestTable.TABLE).insert(new TestContentClass(12, "bc"));
        List<TestContentClass> savedElements = SQLite.get().query(TestTable.TABLE).all().execute();
        assertEquals(elements.size(), savedElements.size());
        assertEquals("bc", savedElements.get(1).getText());
    }

    @Test
    public void testQueryWithSingleObject() throws Exception {
        List<TestContentClass> elements = new ArrayList<>();
        elements.add(new TestContentClass(11, "a"));
        elements.add(new TestContentClass(12, "ab"));
        SQLite.get().insert(TestTable.TABLE).insert(elements);

        TestContentClass element = SQLite.get().query(TestTable.TABLE)
                .object().where(TestTable.Columns.ID + "=?").whereArgs(new String[]{"12"}).execute();
        assertEquals(elements.get(1), element);
    }

    @Test
    public void testQueryList() throws Exception {
        List<TestContentClass> elements = new ArrayList<>();
        elements.add(new TestContentClass(110, "a"));
        elements.add(new TestContentClass(111, "ab"));
        elements.add(new TestContentClass(112, "xaxka"));
        elements.add(new TestContentClass(113, "ab"));
        elements.add(new TestContentClass(114, "abc"));
        SQLite.get().insert(TestTable.TABLE).insert(elements);

        List<TestContentClass> savedElements = SQLite.get().query(TestTable.TABLE)
                .all().where(TestTable.Columns.TEXT + "=?").whereArgs(new String[]{"ab"}).execute();

        assertEquals(2, savedElements.size());
        assertEquals(elements.get(1), savedElements.get(0));
        assertEquals(elements.get(3), savedElements.get(1));
    }

    @Test
    public void testEmptyElement() throws Exception {
        TestContentClass element = SQLite.get().query(TestTable.TABLE).object().execute();
        assertNull(element);
    }

    @Test
    public void testEmptyList() throws Exception {
        List<TestContentClass> elements = SQLite.get().query(TestTable.TABLE).all().execute();
        assertTrue(elements.isEmpty());
    }

    @Test
    public void testUpdateRow() throws Exception {
        TestContentClass element = new TestContentClass(123321, "abc");
        SQLite.get().insert(TestTable.TABLE).insert(element);

        TestContentClass update = new TestContentClass(123321, "xyz");
        int count = SQLite.get().update(TestTable.TABLE).insert(update)
                .where(TestTable.Columns.ID + "=?").whereArgs(new String[]{"123321"}).execute();
        assertEquals(1, count);

        TestContentClass updated = SQLite.get().query(TestTable.TABLE).object().execute();
        assertEquals(update, updated);
    }

    @Test
    public void testNoUpdate() throws Exception {
        TestContentClass element = new TestContentClass(123321, "abc");
        SQLite.get().insert(TestTable.TABLE).insert(element);

        TestContentClass update = new TestContentClass(123322, "xyz");
        int count = SQLite.get().update(TestTable.TABLE).insert(update)
                .where(TestTable.Columns.ID + "=?").whereArgs(new String[]{"1233212"}).execute();
        assertEquals(0, count);

        TestContentClass notUpdated = SQLite.get().query(TestTable.TABLE).object().execute();
        assertEquals(element, notUpdated);
    }

    @Test
    public void testDeleteAll() throws Exception {
        List<TestContentClass> elements = new ArrayList<>();
        elements.add(new TestContentClass(11, "a"));
        elements.add(new TestContentClass(12, "ab"));
        SQLite.get().insert(TestTable.TABLE).insert(elements);

        int count = SQLite.get().delete(TestTable.TABLE).execute();
        assertEquals(2, count);

        elements = SQLite.get().query(TestTable.TABLE).all().execute();
        assertTrue(elements.isEmpty());
    }

    @Test
    public void testDeleteByParameters() throws Exception {
        List<TestContentClass> elements = new ArrayList<>();
        elements.add(new TestContentClass(110, "a"));
        elements.add(new TestContentClass(111, "ab"));
        elements.add(new TestContentClass(112, "xaxka"));
        elements.add(new TestContentClass(113, "ab"));
        elements.add(new TestContentClass(114, "abc"));
        SQLite.get().insert(TestTable.TABLE).insert(elements);

        int count = SQLite.get().delete(TestTable.TABLE).where(TestTable.Columns.TEXT + "=?")
                .whereArgs(new String[]{"ab"}).execute();
        assertEquals(2, count);

        List<TestContentClass> leftElements = SQLite.get().query(TestTable.TABLE).all().execute();

        assertEquals(3, leftElements.size());
        assertEquals(elements.get(0), leftElements.get(0));
        assertEquals(elements.get(2), leftElements.get(1));
        assertEquals(elements.get(4), leftElements.get(2));
    }

    @Test
    public void testSQLite() throws Exception {
        TestContentClass test = new TestContentClass(1, "aaaa");
        SQLite.get()
                .insert(TestTable.TABLE)
                .insert(test);

        List<TestContentClass> all = SQLite.get()
                .query(TestTable.TABLE)
                .all()
                .execute();
        assertEquals(1, all.size());
        assertTrue(test.equals(all.get(0)));

        test.setText("BBBBB");
        int rows = SQLite.get()
                .update(TestTable.TABLE)
                .insert(test)
                .where("id=?")
                .whereArgs(new String[]{"1"})
                .execute();

        all = SQLite.get()
                .query(TestTable.TABLE)
                .all()
                .execute();
        assertEquals(1, rows);
        assertEquals(1, all.size());
        assertTrue(test.equals(all.get(0)));

        SQLite.get()
                .delete(TestTable.TABLE)
                .execute();

        assertTrue(SQLite.get()
                .query(TestTable.TABLE)
                .all()
                .execute()
                .isEmpty());
    }

    @After
    public void tearDown() throws Exception {
        mProvider.shutdown();
    }
}
