import './css/App.css'
import {ArcElement, Chart as ChartJS, Legend, RadialLinearScale, Tooltip} from "chart.js";
import {Route, Routes} from "react-router-dom";
import {useEffect} from "react";
import Header from "./components/Header.tsx";
import Footer from "./components/Footer.tsx";
import HomeSite from "./components/sites/HomeSite.tsx";
import NewMealSite from "./components/sites/NewMealSite.tsx";
import NewUser from "./components/sites/NewUser.tsx";


ChartJS.register(RadialLinearScale, ArcElement, Tooltip, Legend);

function App() {

    useEffect(() => {

        }, [])

  return (<>


      <div className={"container"}>

          <Header />

          <Routes>
              <Route path={"/"} element={ <HomeSite /> } />
              <Route path={"/meal/new"} element={ <NewMealSite /> } />
              <Route path={"/meal/change/%id%"} element={ <NewMealSite /> } />
              <Route path={"/user/new"} element={ <NewUser /> } />
          </Routes>

          <Footer />

      </div>
    <p className={ "mt-3" }>&copy;2025 by Der Supernerd</p>
    </>

  )
}

export default App
