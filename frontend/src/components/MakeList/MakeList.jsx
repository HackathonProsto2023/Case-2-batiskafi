import React from "react";

const MakeList = ({ array }) => {
    

    return (
        <div className="box__wrapper">
            <div className="box__list">
                {
                    array.map((value, index) => <div className="box__item" key={index}>{value}</div>)
                }
            </div>
        </div>

    )
}

export default MakeList;