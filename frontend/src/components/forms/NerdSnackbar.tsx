import {Alert, Snackbar} from "@mui/material";

type Props = {
    openSnackbar: boolean;
    setOpenSnackbar: (open: boolean) => void;
    snackbarMessage: string;
    snackbarSeverity: "success" | "error";
};

export default function NerdSnackbar({
                                         openSnackbar,
                                         setOpenSnackbar,
                                         snackbarMessage,
                                         snackbarSeverity
                                     }: Readonly<Props>) {

    const handleClose = () => {
        setOpenSnackbar(false);
    };

    return (
        <Snackbar
            open={openSnackbar}
            autoHideDuration={5000}
            onClose={handleClose}
            anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
        >
            <Alert
                onClose={handleClose}
                severity={snackbarSeverity}
                sx={{ width: '100%' }}
            >
                {snackbarMessage}
            </Alert>
        </Snackbar>
    );
}