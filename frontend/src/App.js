import React, { useState } from 'react';
import { TextField, Button, Box, Typography, Grid, Paper, FormControlLabel, Checkbox, Table, TableBody, TableCell, TableRow, TableHead, TableContainer } from '@mui/material';
import './App.css';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import axios from 'axios';

function App() {
  const demoCode = "import java.util.Scanner;\n" +
    "\n" +
    "public class Fibonacci {\n" +
    "    public static void main(String[] args) {\n" +
    "        Scanner scanner = new Scanner(System.in);\n" +
    "        System.out.print(\"Enter the number of terms: \");\n" +
    "        int n = scanner.nextInt();\n" +
    "\n" +
    "        int[] fibSequence = fibonacci(n);\n" +
    "        System.out.print(\"Fibonacci Sequence: \");\n" +
    "        for (int i = 0; i < n; i++) {\n" +
    "            System.out.print(fibSequence[i] + \" \");\n" +
    "        }\n" +
    "    }\n" +
    "\n" +
    "    public static int[] fibonacci(int n) {\n" +
    "        int[] fibSequence = new int[n];\n" +
    "        fibSequence[0] = 0;\n" +
    "        fibSequence[1] = 1;\n" +
    "\n" +
    "        for (int i = 2; i < n; i++) {\n" +
    "            fibSequence[i] = fibSequence[i - 1] + fibSequence[i - 2];\n" +
    "        }\n" +
    "        return fibSequence;\n" +
    "    }\n" +
    "}\n";
  const [classCode, setClassCode] = useState('');
  const [className, setClassName] = useState('');
  const [checkStyleConfig, setCheckStyleConfig] = useState('');
  const [checkstyleResult, setCheckstyleResult] = useState({ status: "pending" });
  const [inferResult, setInferResult] = useState({ status: "pending" });
  const [symbolicExecutionResult, setSymbolicExecutionResult] = useState({ status: "pending" });
  const [useDefaultCheckStyle, setUseDefaultCheckStyle] = useState(false);

  const [results, setResults] = useState([]);

  const handleCheckStyleConfigChange = (event) => {
    setCheckStyleConfig(event.target.value);
    if (event.target.value !== '') {
      setUseDefaultCheckStyle(false);
    }
  };

  const handleCheckboxChange = (event) => {
    setUseDefaultCheckStyle(event.target.checked);
    if (event.target.checked) {
      setCheckStyleConfig('');
    }
  };

  const handleSubmit = async () => {
    // Start Checkstyle verification
    setCheckstyleResult('Fetching...');
    try {
      const responseCheckstyle = await axios.post('http://localhost:8080/verifyWithCheckstyle', { "code": classCode, "fileName": className, checkStyleConfig });
      setCheckstyleResult(responseCheckstyle.data);
    } catch (error) {
      setCheckstyleResult('Failed to verify');
    }

    // Start Infer verification
    setInferResult('Fetching...');
    try {
      const responseInfer = await axios.post('http://localhost:8080/verifyWithInfer', { "code": classCode, "fileName": className });
      setInferResult(responseInfer.data);
    } catch (error) {
      setInferResult('Failed to verify');
    }

    // Start Symbolic Execution verification
    setSymbolicExecutionResult('Fetching...');
    try {
      const responseSymbolic = await axios.post('http://localhost:8080/verifyWithSymbolicExecution', { "code": classCode, "fileName": className });
      setSymbolicExecutionResult(responseSymbolic.data);
    } catch (error) {
      setSymbolicExecutionResult('Failed to verify');
    }
  };

  return (
    <Box sx={{ display: 'flex', m: 2, height: '100vh' }}>
      <Box sx={{ width: '70%' }}>

        <Typography variant="h4" gutterBottom>
          Verification Dashboard
        </Typography>
        {/* Button to set all fields to default values */}
        <Button
          variant="contained"
          color="secondary"
          onClick={() => {
            setClassName('Fibonacci.java');
            setClassCode(demoCode);
            handleCheckboxChange({ "target": { "checked": checkStyleConfig.length > 0 ? false : true } })
            // Set other default states if necessary
          }}
          sx={{ mb: 2 }}
        >
          Fill with Demo Values
        </Button>

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
          onChange={handleCheckStyleConfigChange}
          disabled={useDefaultCheckStyle}
        />
        <FormControlLabel
          control={<Checkbox checked={useDefaultCheckStyle} onChange={handleCheckboxChange} />}
          label="Use Default Checkstyle Configuration"
        />
        <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2, display: 'flex' }}>
          Submit
        </Button>
        <Grid container spacing={2} alignItems="flex-start" justifyContent="center">
          <Grid item xs={4}>
            <Paper style={{ position: 'sticky', top: 0, zIndex: 10, background: getPaperColor(checkstyleResult) }}>
              <Typography variant="h6" gutterBottom>
                Checkstyle
              </Typography>
            </Paper>
            <div style={{ maxHeight: 200, overflow: 'auto', padding: 8,background:"cyan" }}>
              {checkstyleResult.result}
            </div>
          </Grid>

          <Grid item xs={4}>
            <Paper style={{ position: 'sticky', top: 0, zIndex: 10, background: getPaperColor(inferResult) }}>
              <Typography variant="h6" gutterBottom>
                Infer
              </Typography>
            </Paper>
            <div style={{ maxHeight: 200, overflow: 'auto', padding: 8,background:"cyan" }}>
              {inferResult.result}
            </div>
          </Grid>

          <Grid item xs={4}>
            <Paper style={{ position: 'sticky', top: 0, zIndex: 10, background: getPaperColor(symbolicExecutionResult) }}>
              <Typography variant="h6" gutterBottom>
                Symbolic Execution
              </Typography>
            </Paper>
            <div style={{ maxHeight: 200, overflow: 'auto', padding: 8,background:"cyan" }}>
              {symbolicExecutionResult.result}
            </div>
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
  if (result.status == "pending") {
    return '#FFFF00'; // Yellow for fetching
  } else if (result.status == 0) {
    return '#00FF00'; // Green for success
  } else if (result.status == 1) {
    return 'red'; // Green for success
  } else {
    return '#D3D3D3'; // Grey for initial or error state
  }
}

export default App;
