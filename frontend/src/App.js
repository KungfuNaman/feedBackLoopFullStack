import React, { useState } from 'react';
import { TextField, Button, Box, Typography, Grid, Paper,Checkbox, Table, TableBody, TableCell, TableRow, TableHead, TableContainer } from '@mui/material';
import './App.css';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import axios from 'axios';

function App() {
  const [classCode, setClassCode] = useState('');
  const [className, setClassName] = useState('');
  const [checkStyleConfig, setCheckStyleConfig] = useState('');
  const [checkstyleResult, setCheckstyleResult] = useState('Pending...');
  const [inferResult, setInferResult] = useState('Pending...');
  const [symbolicExecutionResult, setSymbolicExecutionResult] = useState('Pending...');
  const [results, setResults] = useState([]);


  const handleSubmit = async () => {
    // Start Checkstyle verification
    setCheckstyleResult('Fetching...');
    try {
      const responseCheckstyle = await axios.get('/api/checkstyle', { params: { classCode, checkStyleConfig } });
      setCheckstyleResult(responseCheckstyle.data);
    } catch (error) {
      setCheckstyleResult('Failed to verify');
    }

    // Start Infer verification
    setInferResult('Fetching...');
    try {
      const responseInfer = await axios.get('/api/infer', { params: { classCode } });
      setInferResult(responseInfer.data);
    } catch (error) {
      setInferResult('Failed to verify');
    }

    // Start Symbolic Execution verification
    setSymbolicExecutionResult('Fetching...');
    try {
      const responseSymbolic = await axios.get('/api/symbolic', { params: { className } });
      setSymbolicExecutionResult(responseSymbolic.data);
    } catch (error) {
      setSymbolicExecutionResult('Failed to verify');
    }
  };

  return (
    <Box sx={{ display: 'flex', m: 2,height:'100vh' }}>
      <Box sx={{ width: '70%' }}>

        <Typography variant="h4" gutterBottom>
          Verification Dashboard
        </Typography>
        <TextField
          label="Class Name"
          fullWidth
          margin="normal"
          variant="outlined"
          value={className}
          onChange={(e) => setClassName(e.target.value)}
        />
        <TextField
          label="Class Code"
          multiline
          rows={4}
          fullWidth
          margin="normal"
          variant="outlined"
          value={classCode}
          onChange={(e) => setClassCode(e.target.value)}
        />
        <TextField
          label="Checkstyle Configuration"
          multiline
          rows={4}
          fullWidth
          margin="normal"
          variant="outlined"
          value={checkStyleConfig}
          onChange={(e) => setCheckStyleConfig(e.target.value)}
        />
        <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
          Submit
        </Button>
        <Grid container spacing={2} alignItems="center" justifyContent="center" sx={{ mt: 4 }}>
          <Grid item xs={3}>
            <Typography variant="h6" gutterBottom>
              Checkstyle
            </Typography>
            <Paper style={{ maxHeight: 200, overflow: 'auto', padding: 8, background: getPaperColor(checkstyleResult) }}>
              {checkstyleResult}
            </Paper>
          </Grid>
          <Grid item>
            <ArrowForwardIosIcon />
          </Grid>
          <Grid item xs={3}>
            <Typography variant="h6" gutterBottom>
              Infer
            </Typography>
            <Paper style={{ maxHeight: 200, overflow: 'auto', padding: 8, background: getPaperColor(inferResult) }}>
              {inferResult}
            </Paper>
          </Grid>
          <Grid item>
            <ArrowForwardIosIcon />
          </Grid>
          <Grid item xs={3}>
            <Typography variant="h6" gutterBottom>
              Symbolic Execution
            </Typography>
            <Paper style={{ maxHeight: 200, overflow: 'auto', padding: 8, background: getPaperColor(symbolicExecutionResult) }}>
              {symbolicExecutionResult}
            </Paper>
          </Grid>
        </Grid>
      </Box>
      <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: '30%', ml: 2, height: '100%' }}>
        <Box sx={{ flexGrow: 8, overflow: 'auto' }}> {/* Use flexGrow for proportional sizing */}
          <TableContainer component={Paper}>
            <Table stickyHeader>
              <TableHead>
                <TableRow>
                  <TableCell>Checkstyle</TableCell>
                  <TableCell>Infer</TableCell>
                  <TableCell>Symbolic Execution</TableCell>
                  <TableCell>Issues</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {results.map((result, index) => (
                  <TableRow key={index}>
                    <TableCell>{result.checkstyle}</TableCell>
                    <TableCell>{result.infer}</TableCell>
                    <TableCell>{result.symbolic}</TableCell>
                    <TableCell>{result.issues}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Box>
        <Box sx={{ flexGrow: 2 }}> {/* Use flexGrow for proportional sizing */}
          <Typography>Graph Placeholder</Typography>
        </Box>
      </Box>
    </Box>
  );
}

function getPaperColor(result) {
  if (result.includes('Fetching')) {
    return '#FFFF00'; // Yellow for fetching
  } else if (result.includes('Verified') || !result.includes('Failed')) {
    return '#00FF00'; // Green for success
  } else {
    return '#D3D3D3'; // Grey for initial or error state
  }
}

export default App;
