import './css/App.css'
import LoginIcon from '@mui/icons-material/Login';
import {Box, Button, IconButton, LinearProgress, ListItemButton, ListItemText} from "@mui/material";
import {ArcElement, Chart as ChartJS, Legend, RadialLinearScale, Tooltip} from "chart.js";
import {Gauge, gaugeClasses} from '@mui/x-charts/Gauge';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';


const LoginButtonStyle = { color:"#394738", borderColor: "#394738", marginRight: "30px" }
const ButtonStyleLightFull = { backgroundColor: "#eeede9", color: "#394738", width: "200px" }
const ButtonStyleDarkFull = { backgroundColor: "#394738", color: "#eeede9", width: "200px" }
const ButtonStyleOrangeFull = { backgroundColor: "#f68247", color: "#eeede9", width: "200px" }

ChartJS.register(RadialLinearScale, ArcElement, Tooltip, Legend);

    const data = {
        labels: ["Protein", "Kohlenhydrate", "Fette", "Ballaststoffe", "Vitamine"],
        datasets: [
            {
                label: "Makronährstoffe",
                data: [30, 50, 20, 10, 40], // Beispielwerte
                backgroundColor: [
                    "rgba(255, 99, 132, 0.5)",
                    "rgba(54, 162, 235, 0.5)",
                    "rgba(255, 206, 86, 0.5)",
                    "rgba(75, 192, 192, 0.5)",
                    "rgba(153, 102, 255, 0.5)",
                ],
                borderWidth: 1,
            },
        ],
    };


function App() {


  return (<>
      <div className={"container"}>

              <div className={"row pt-2"}>
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

                      <p className={"font-lg mb-0"}>2564 kcal</p>
                      <p >Alter: 43 &bull; Gewicht: 80 kg &bull; Größe: 183 cm</p>
                      <Button variant={"contained"} style={ ButtonStyleOrangeFull }>test</Button>
                  </div>
              </div>

              <div className={"row "}>

                  <div className={"col-md-12 col-lg-6 text-start p-5"}>

                      <h3 className={"mb-4"}>Guten Tag %Vorname%,</h3>

                      <p>schön, dass Du vorbei schaust! Was möchtest Du machen?</p>

                      <ListItemButton component="a" href="#simple-list" sx={{color: '#f68247'}}>
                          <ListItemText primary="Neue Mahlzeit / neuen Snack hinzufügen" />
                      </ListItemButton>

                      <ListItemButton component="a" href="#simple-list" sx={{color: '#f68247'}}>
                          <ListItemText primary="Profildaten bearbeiten" />
                      </ListItemButton>

                  </div>
                  <div className={"col-md-12 col-lg-6 p-4 mt-3"}>
                      <div className={"col-12 box1  pt-2"}>

                          <div className={"row mb-3"}>
                              <div className={"col-12"}>
                                  <h2 className={"fw-bold"}>Heute</h2>
                              </div>
                          </div>

                          <div className={"row"}>
                                  <Gauge
                                      height={200}
                                      width={400}
                                      value={1950}
                                      valueMax={2564}
                                      startAngle={-110}
                                      endAngle={110}
                                      cornerRadius="50%"
                                      sx={{
                                          [`& .${gaugeClasses.valueText}`]: {
                                              fontSize: 30,
                                              transform: 'translate(0px, 0px)',
                                          },
                                          [`& .${gaugeClasses.valueArc}`]: {
                                              fill: '#f68247',
                                          },
                                      }}
                                      text={
                                          ({ value, valueMax }) => `${value} / ${valueMax}`
                                      }
                                  />
                          </div>

                                <div className={"row p-5"}>
                                  <div className={"col-4 p-3 "}>
                                      <Box sx={{ width: '100%', fill: "#f68247" }}>
                                          {/* Setze einen statischen Wert für den Fortschritt */}
                                          <LinearProgress variant="determinate" value={50}  sx={{
                                              backgroundColor: '#e0e0e0', // Hintergrundfarbe (Track)
                                              '& .MuiLinearProgress-bar': {
                                                  backgroundColor: '#799a61', // Fortschrittsfarbe (Bar)
                                              },
                                          }}/>
                                      </Box>
                                      <p>Fett</p>

                                  </div>

                                  <div className={"col-4 p-3 "}>
                                      <Box sx={{ width: '100%', fill: "#f68247" }}>
                                          {/* Setze einen statischen Wert für den Fortschritt */}
                                          <LinearProgress variant="determinate" value={70}  sx={{
                                              backgroundColor: '#e0e0e0', // Hintergrundfarbe (Track)
                                              '& .MuiLinearProgress-bar': {
                                                  backgroundColor: '#799a61', // Fortschrittsfarbe (Bar)
                                              },
                                          }}/>
                                      </Box>
                                      <p>KH</p>

                                  </div>

                                  <div className={"col-4 p-3 "}>
                                      <Box sx={{ width: '100%', fill: "#f68247" }}>
                                          {/* Setze einen statischen Wert für den Fortschritt */}
                                          <LinearProgress variant="determinate" value={30}  sx={{
                                              backgroundColor: '#e0e0e0', // Hintergrundfarbe (Track)
                                              '& .MuiLinearProgress-bar': {
                                                  backgroundColor: '#799a61', // Fortschrittsfarbe (Bar)
                                              },
                                          }}/>
                                      </Box>
                                      <p>Protein</p>
                                  </div>
                                </div>
                          </div>
                      </div>

                  </div>

                  <div className={"row footer-container align-content-"}>
          <div className={"col-md-12 col-lg-6 footer-left"}>
                    &nbsp;
                </div>
                <div className={"col-md-12 col-lg-6"}>

                </div>

              </div>

          </div>
    <p>&copy;2025 by Der Supernerd</p>
    </>

  )
}

export default App
