import React from "react";

const DataBaseAsideList = ({ tables }) => {

    return (
        <ul className="database__aside-list">
            <li className="database__aside-list" key={0}>Показать всю базу данных</li>
            {
                tables.map((table, index) =>
                    <li
                        className="database__aside-list"
                        key={index + 1}
                    >Таблица {table.tableName}</li>
                )
            }
        </ul>
    )
}

export default DataBaseAsideList;