import * as React from "react";
import { motion } from "framer-motion";
import { MenuItemMobile } from "../MenuItemMobile/MenuItemMobile";

const variants = {
  open: {
    transition: { staggerChildren: 0.07, delayChildren: 0.2 }
  },
  closed: {
    transition: { staggerChildren: 0.05, staggerDirection: -1 }
  }
};

export const NavMobile = () => (
  <motion.ul variants={variants}>
    <MenuItemMobile/>
  </motion.ul>
);