import * as React from "react";
import { motion } from "framer-motion";

const variants = {
  open: {
    y: 0,
    opacity: 1,
    transition: {
      y: { stiffness: 1000, velocity: -100 }
    }
  },
  closed: {
    y: 50,
    opacity: 0,
    transition: {
      y: { stiffness: 1000 }
    }
  }
};


export const MenuItemMobile = () => {
  
  return (
    <motion.ul
      className="nav__list--mobile"
      variants={variants}
      whileHover={{ scale: 1.1 }}
      whileTap={{ scale: 0.95 }}
    >
      <li className="nav__list-item--mobile"><a href="/" className="nav__list-link">Получить БД</a></li>
      <li className="nav__list-item--mobile"><a href="/" className="nav__list-link">О проекте</a></li>
      <li className="nav__list-item--mobile"><a href="/" className="nav__list-link">Сотрудничество</a></li>
      <li className="nav__list-item--mobile"><a href="/" className="nav__list-link">Контакты</a></li>
    </motion.ul>
  );
};
