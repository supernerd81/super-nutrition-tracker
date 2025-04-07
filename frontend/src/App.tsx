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
import {AppUserDetails} from "./components/model/AppUserDetails.ts";
import ProtectedRoutes from "./components/ProtectedRoutes.tsx";
import {AppUser} from "./components/model/AppUser.ts";

ChartJS.register(RadialLinearScale, ArcElement, Tooltip, Legend);

function App() {

    const [appUser, setAppUser] = useState<AppUser | undefined | null>(undefined)
    const [appUserDetails, setAppUserDetails] = useState<AppUserDetails | undefined | null>(undefined)
    const [kcalPerDay, setKcalPerDay] = useState<number>(2648)

    function fetchUserData() {
        axios.get("/api/auth/me")
            .then(r => {
                const user = r.data

                setAppUser(user)
                console.log('User: ', user)

                if(user?.id) {
                    axios.get('api/user/' + user.id)
                        .then(r => {
                            setAppUserDetails(r.data)
                            console.log('User details: ', r.data)
                        })
                        .catch(error => {
                            console.error('Error fetching User data details: ', error)
                        })
                }
            })
            .catch(error => {
                console.error('Error fetching User data: ', error)
            })
    }

    useEffect(fetchUserData, [])

  return (<>
      <div className={"container"}>

          <Header kcalPerDay={kcalPerDay} appUser={appUser} appUserDetails={appUserDetails}/>

          <Routes>
              <Route path={"/"} element={ <HomeSite appUserDetails={ appUserDetails } kcalPerDay={kcalPerDay} /> } />

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
