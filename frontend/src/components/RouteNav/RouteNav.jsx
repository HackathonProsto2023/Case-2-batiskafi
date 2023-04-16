import Footer from '../Footer/Footer';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Navigation from '../Navigation/Navigation';
import StartPage from '../../pages/StartPage/StartPage';
import DBPage from '../../pages/DBPage/DBPage';
import AboutProject from '../../pages/AboutProject/AboutProject'
import Cooperation from '../../pages/Cooperation/Cooperation'
import Contacts from '../../pages/Contacts/Contacts'

const RouteNav = function(){
  
    return (
      <BrowserRouter>
        <div className='header'>
            <Navigation/>
        </div>
        <div className="main__container">
          <Routes>
            <Route path='/' element={<StartPage/>}/>
            <Route path='/dbpage' element={<DBPage/>}/>
            <Route path='/aboutproject' element={<AboutProject/>}/>
            <Route path='/cooperation' element={<Cooperation/>}/>
            <Route path='/contacts' element={<Contacts/>}/>
          </Routes>
          <div className="footer">
            <Footer/>
          </div>
        </div>
      </BrowserRouter>
    );
}

export default RouteNav