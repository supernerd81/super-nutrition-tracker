import './css/App.css'
import {ArcElement, Chart as ChartJS, Legend, RadialLinearScale, Tooltip} from "chart.js";
import {Route, Routes} from "react-router-dom";
import {useEffect, useState} from "react";
import Header from "./components/Header.tsx";
import Footer from "./components/Footer.tsx";
import HomeSite from "./components/sites/HomeSite.tsx";
import NewMealSite from "./components/sites/NewMealSite.tsx";
import UpdateProfile from "./components/sites/UpdateProfile.tsx";
import axios from 'axios';
import ProtectedRoutes from "./components/ProtectedRoutes.tsx";
import {AppUser} from "./components/model/AppUser.ts";

ChartJS.register(RadialLinearScale, ArcElement, Tooltip, Legend);

function App() {

    const [appUser, setAppUser] = useState<AppUser | undefined | null>(undefined)

    function fetchUserData() {
        axios.get("/api/auth/me")
            .then(r => {
                const user = r.data
                setAppUser(user)

            })
            .catch(error => {
                console.error('Error fetching User data: ', error)
            })
    }

    useEffect(fetchUserData, [])

    console.log(`App ${appUser}`)
    return (<>
      <div className={"container"}>

          <Header appUser={appUser} />

          <Routes>
              <Route path={"/"} element={ <HomeSite appUser={ appUser }  /> } />

              <Route element={<ProtectedRoutes appUser={appUser} />} >
                  <Route path={"/meal/new"} element={ <NewMealSite /> } />
                  <Route path={"/meal/change/%id%"} element={ <NewMealSite /> } />
                  <Route path={"/user/new"} element={ <UpdateProfile appUser={appUser} setAppUser={setAppUser}/> } />
              </Route>
          </Routes>

          <Footer />

      </div>
    <p className={ "mt-3" }>&copy;2025 by Der Supernerd</p>
    </>

  )
}

export default App
