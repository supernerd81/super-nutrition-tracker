import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

/// <reference types="vite/client" />

export default defineConfig({
    plugins: [react()],
    server: {
        proxy: {
            '/api': 'http://localhost:8080',
            changeOrigin: true,
            secure: false,
        }
    }
})