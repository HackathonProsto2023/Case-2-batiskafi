import React from "react";
import "../Modal/Modal.scss"

const Modal = ({active, setActive, children }) => {

    return (
        <div className={active ? "modal": "modal active"} onClick={()=> setActive(true)}>
            <div className={active ? "modal__content": "modal__content active"} onClick={(e)=>e.stopPropagation()}>
                {children}
            </div>
        </div>

    )
}

export default Modal;