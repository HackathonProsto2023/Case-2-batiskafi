import React from "react";
import Xarrow from 'react-xarrows';


const Arrow = ({ tables }) => {

    const obj = [{
        from: String,
        to: String
    }]

    tables.map(value => {
        if (value.references.length !== 0) {
            for (let i = 0; i < value.references.length; i++) {
                obj.push({ from: value.tableName, to: value.references[i] })
            }
        }
    })

    obj.shift()


    return (
        <div>
            {
                obj.map((val, index) =>

                    <Xarrow start={val.from} end={val.to} key={index + 1} />
                )}
        </div>
    )
}

export default Arrow;