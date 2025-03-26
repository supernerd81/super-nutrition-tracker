import './css/App.css'
import LoginIcon from '@mui/icons-material/Login';
import {Button} from "@mui/material";

const LoginButtonStyle = { color:"#394738", borderColor: "#394738", marginRight: "30px" }
const ButtonStyleLightFull = { backgroundColor: "#eeede9", color: "#394738", width: "200px" }
const ButtonStyleDarkFull = { backgroundColor: "#394738", color: "#eeede9", width: "200px" }
const ButtonStyleOrangeFull = { backgroundColor: "#f68247", color: "#eeede9", width: "200px" }

function App() {


  return (
      <div className={"main-container"}>
          <div className={"headline-container container"}>

              <div className={"row"}>
                  <div className={"col-md-12 col-lg-6"}>
                      <h1 className={"headline headline-font"}>Super Nutrition Tracker</h1>
                  </div>
                  <div className={"col-md-12 col-lg-6 text-lg-end align-content-center"}>
                      <Button variant="outlined" startIcon={<LoginIcon />} color={"info"} style={ LoginButtonStyle }>
                          Login
                      </Button>
                  </div>
              </div>

              <div className={"row header-container"}>
                  <div className={"col-md-12 col-lg-6 header-left text-lg-start"}>
                      <h2>Track your Nutrition</h2>
                      <Button style={ ButtonStyleLightFull } variant={"contained"} className={"mt-3"}>test</Button>
                  </div>
                  <div className={"col-md-12 col-lg-6 header-right text-lg-start"}>
                      <h2>Grundumsatz pro Tag</h2>

                      <p className={"font-lg"}>2564 kcal</p>
                      <Button variant={"contained"} style={ ButtonStyleOrangeFull }>test</Button>
                  </div>
              </div>

              <div className={"row"}>

                  <div className={"col-md-12 col-lg-6"}>

                  </div>
                  <div className={"col-md-12 col-lg-6 p-4 mt-3"}>
                      <div className={"col-12 box1"}>
test
                      </div>
                  </div>

              </div>

              <div className={"row"}>



              </div>

          </div>

      </div>
  )
}

export default App
