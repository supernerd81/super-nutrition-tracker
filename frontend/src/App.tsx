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
import {AppUser} from "./components/model/AppUser.ts";
import ProtectedRoutes from "./components/ProtectedRoutes.tsx";

ChartJS.register(RadialLinearScale, ArcElement, Tooltip, Legend);

function App() {

    const [appUser, setAppUser] = useState<AppUser | undefined | null>(undefined)

    function fetchUserData() {
        axios.get("/api/user/c44aea73-1e30-452c-878f-de8f1bcc55ba")
            .then(response => {
                setAppUser(response.data)
                console.log(response.data)
            })
            .catch(error => {
                console.error('Error fetching User data: ', error)
            })
    }

    useEffect(fetchUserData, [])

  return (<>
      <div className={"container"}>

          <Header kcalPerDay={2848} appUser={appUser}/>

          <Routes>
              <Route path={"/"} element={ <HomeSite appUser={ appUser } /> } />

              <Route element={<ProtectedRoutes appUser={appUser} />} >
                  <Route path={"/meal/new"} element={ <NewMealSite /> } />
                  <Route path={"/meal/change/%id%"} element={ <NewMealSite /> } />
                  <Route path={"/user/new"} element={ <NewUser /> } />
              </Route>
          </Routes>

          <Footer />

      </div>
    <p className={ "mt-3" }>&copy;2025 by Der Supernerd</p>
    </>

  )
}

export default App
