
import sqlite3


class DatabaseManager(object):

    def __init__(self, db_file):
        self.conn = sqlite3.connect(db_file)
    
    def __del__(self):
        self.conn.close()

    def _execute(self, stmt, values=None):
        """Executes a SQL statement
        
        Arguments:
            stmt {string} -- SQL statement (with placeholder values)
        
        Keyword Arguments:
            values {list} -- List of values to fill in placeholders in the statement (default: {None})
        
        Returns:
            Cursor -- cursor used to execute the SQL statement
        """

        # Sqlite3 lets you use the conn object to create a transaction 
        # via a context manager (i.e., using 'with' keyword)
        with self.conn:
            cursor = self.conn.cursor()
            # Good security practice to use placeholders for real values in
            # SQL statement to prevent users from doing bad things with specially
            # crafted queries
            cursor.execute(stmt, values or [])
            return cursor

    def create_table(self, table_name, columns):
        """Creates a new DB table with the given name and columns
        
        Arguments:
            table_name {string} -- Table name
            columns {dict} -- Dictionary of column names mapped to their data types and constraints
        """
        cols_with_types = [
            f'{col_name} {data_type}'
            for col_name, data_type in columns.items()
        ]

        # Example SQL:
        # CREATE TABLE IF NOT EXISTS bookmarks
        # (
        #     id INTEGER PRIMARY KEY AUTOINCREMENT,
        #     title TEXT NOT NULL,
        #     url TEXT NOT NULL,
        #     notes TEXT,
        #     date_added TEXT NOT NULL
        # );
        self._execute(
            f'''
            CREATE TABLE IF NOT EXISTS {table_name}
            ({', '.join(cols_with_types)});
            '''
        )
    
    def add(self, table_name, data):
        """Adds a new record to the given table
        
        Arguments:
            table_name {string} -- Table name
            data {dict} -- Dictionary of column names mapped to the column values for the record
        """

        placeholders = ', '.join('?' * len(data))
        col_names = ', '.join(data.keys())
        # Need to convert the dict_values obj to a tuple which is required by sqlite3 execute method
        col_vals = tuple(data.values())

        # Example SQL:
        # INSERT INTO bookmarks
        # (title, url, notes, date_added)
        # VALUES ('GitHub', 'https://github.com', 'A place to store code', 
        #    '2019-02-01T18:46:32.1234567');
        #
        # Insert with placeholders
        # INSERT INTO bookmarks
        # (title, url, notes, date_added)
        # VALUES (?, ?, ?, ?);
        self._execute(
            f'''
            INSERT INTO {table_name}
            ({col_names})
            VALUES ({placeholders});
            ''',
            col_vals
        )

    def delete(self, table_name, criteria):
        """Delete a DB record from the given table
        
        Arguments:
            table_name {string} -- Table name
            criteria {dict} -- Dictionary of column names mapped to value to match on. T
                               This is required so that we don't accidentally delete all records
                               from the table.
        """
        
        placeholders = [f'{column} = ?' for column in criteria.keys()]
        delete_criteria = ' AND '.join(placeholders)

        # Example SQL:
        # DELETE FROM bookmarks
        # WHERE ID = 3;
        self._execute(
            f'''
            DELETE FROM {table_name}
            WHERE {delete_criteria};
            ''',
            tuple(criteria.values())
        )

    def select(self, table_name, criteria=None, order_by=None):
        """Runs a query on the given table
        
        Arguments:
            table_name {string} -- Table name
        
        Keyword Arguments:
            criteria {dict} -- Dictionary of column names mapped to value to match on (default: {None})
            order_by {string} -- Column to sort the results by (default: {None})
        
        Returns:
            Cursor -- cursor used to execute the query
        """
        criteria = criteria or {}

        query = f'SELECT * FROM {table_name}'

        if criteria:
            placeholders = [f'{column} = ?' for column in criteria.keys()]
            select_criteria = ' AND '.join(placeholders)
            query += f' WHERE {select_criteria}'

        if order_by:
            query += f' ORDER BY {order_by}'

        # Example SQL:
        # SELECT * FROM bookmarks
        # WHERE ID = 3
        # ORDER BY title;
        #
        # Select with placeholders:
        # SELECT * FROM bookmarks
        # WHERE ID = ?
        # ORDER BY title;
        return self._execute(
            query,
            tuple(criteria.values())
        )

