import  { useState, useEffect } from 'react';
import { LinearProgress, Box } from '@mui/material';

const LinearProgressBar = () => {
    const [progress, setProgress] = useState(0);

    useEffect(() => {
        // Simuliere den Fortschritt (0 bis 100)
        const interval = setInterval(() => {
            setProgress((prevProgress) => {
                if (prevProgress === 100) {
                    clearInterval(interval);
                    return 100;
                }
                return Math.min(prevProgress + 10, 100);
            });
        }, 1000);

        return () => clearInterval(interval); // Cleanup
    }, []);

    return (
        <Box sx={{ width: '100%' }}>
            <LinearProgress variant="determinate" value={progress} />
        </Box>
    );
};

export default LinearProgressBar;