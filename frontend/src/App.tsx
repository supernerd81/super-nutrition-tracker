import './css/App.css'
import {ArcElement, Chart as ChartJS, Legend, RadialLinearScale, Tooltip} from "chart.js";
import {Route, Routes} from "react-router-dom";
import {useEffect, useState} from "react";
import Header from "./components/Header.tsx";
import Footer from "./components/Footer.tsx";
import HomeSite from "./components/sites/HomeSite.tsx";
import NewMealSite from "./components/sites/NewMealSite.tsx";
import NewUser from "./components/sites/NewUser.tsx";
import axios from 'axios';
import {User} from "./components/model/User.ts";

ChartJS.register(RadialLinearScale, ArcElement, Tooltip, Legend);

function App() {

    const [userData, setUserData] = useState<User>()

    function fetchUserData() {
        axios.get("/api/user/c44aea73-1e30-452c-878f-de8f1bcc55ba")
            .then(response => {
                setUserData(response.data)
                console.log(response.data)
            })
            .catch(error => {
                console.error('Error fetching User data: ', error)
            })
    }

    useEffect(fetchUserData, [])

  return (<>
      <div className={"container"}>

          <Header />

          <Routes>
              <Route path={"/"} element={ <HomeSite firstname={ userData?.firstname ?? "<Vorname>" } /> } />
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
