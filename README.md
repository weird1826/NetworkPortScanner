# NetworkPortScanner

A multithreaded Java-based network port scanner that allows you to scan TCP ports on target hosts. The scanner supports single port scanning, port range scanning, and scanning multiple specific ports.

## Features

- **Multithreaded Scanning**: Uses a thread pool (20 concurrent threads) for fast port scanning
- **Flexible Port Selection**: 
  - Scan a single port
  - Scan a range of ports (e.g., 1-1024)
  - Scan specific ports (e.g., 22,80,443)
- **TCP Port Scanning**: Identifies open and closed TCP ports
- **Automatic Report Generation**: Saves scan results to a text file
- **Command-line Interface**: Easy-to-use CLI with clear arguments

## Requirements

- Java 16 or higher (uses `Stream.toList()` introduced in Java 16)
- No external dependencies required

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/<username>/NetworkPortScanner.git
   cd NetworkPortScanner
   ```

2. Compile the source files:
   ```bash
   javac -d out src/*.java
   ```

## Usage

Run the scanner from the compiled output directory:

```bash
java -cp out PortScanner <options>
```

### Command-line Options

| Option | Short | Description |
|--------|-------|-------------|
| `--ipaddr` | `-ip` | Target IP address to scan (required) |
| `--port` | `-p` | Single port number to scan |
| `--range` | `-r` | Port range to scan (e.g., 1-1024) |
| `--ports` | `--pts` | Comma-separated list of ports (e.g., 22,80,443) |

**Note**: You must use exactly one of `--port`, `--range`, or `--ports`.

### Examples

**Scan a single port:**
```bash
java -cp out PortScanner -ip 192.168.1.1 -p 80
```

**Scan a port range:**
```bash
java -cp out PortScanner -ip 192.168.1.1 -r 1-1024
```

**Scan specific ports:**
```bash
java -cp out PortScanner --ipaddr 192.168.1.1 --ports 22,80,443,8080
```

**Scan localhost:**
```bash
java -cp out PortScanner -ip 127.0.0.1 -r 1-100
```

### Sample Output

```
Initializing Scan Job ---
	[DEBUG] Setting scan for port range: 1-100

Running All Scans in Queue (range): ---
	Starting Multithreaded TCP Scan on: 127.0.0.1
	[INFO] All scan jobs submitted. Waiting...
	[INFO] Scan complete. Total ports scanned: 100
--- OPEN PORTS FOUND ---
    [+] Port 22 is OPEN.
    [+] Port 80 is OPEN.
--- Summary: ---
	Total open ports: 2

	[Info] Saving report to scan_report_for_127.0.0.1.txt...
	[Info] Report saved successfully.

Total Scan Time: 245ms ---
```

## Project Structure

```
NetworkPortScanner/
├── src/
│   ├── PortScanner.java      # Main entry point with CLI argument parsing
│   ├── Scan.java             # Abstract base class for scan types
│   ├── TcpScan.java          # TCP port scanning implementation
│   ├── UdpScan.java          # UDP scan placeholder (not yet implemented)
│   ├── PortScanResult.java   # Holds result data for a single port scan
│   ├── ScannerConfig.java    # Configuration settings for scans
│   └── IDisplayable.java     # Interface for displayable objects
├── README.md
├── .gitignore
└── NetworkPortScanner.iml    # IntelliJ IDEA module file
```

### Class Overview

- **PortScanner**: Main class that handles CLI argument parsing and orchestrates scans
- **Scan**: Abstract base class defining the scan interface
- **TcpScan**: Implements TCP port scanning using socket connections
- **UdpScan**: Placeholder for future UDP scanning functionality
- **PortScanResult**: Data class storing port number and open/closed status
- **ScannerConfig**: Configuration holder for scan settings (timeout, target IP)
- **IDisplayable**: Interface for classes that can display their state

## How It Works

1. The scanner parses command-line arguments to determine the target IP and ports to scan
2. A `TcpScan` job is created with the specified configuration
3. The scan uses a fixed thread pool to test multiple ports concurrently
4. For each port, a TCP socket connection is attempted with a 200ms timeout
5. Results are collected and filtered to show only open ports
6. A summary report is generated and saved to a file

## Limitations

- Only TCP scanning is currently implemented
- Default connection timeout is 200ms (may need adjustment for high-latency networks)
- UDP scanning is a placeholder and not functional

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request

## License

This project is open source. Feel free to use, modify, and distribute as needed.

## Disclaimer

This tool is intended for educational purposes and authorized network testing only. Always obtain proper authorization before scanning networks or systems that you do not own. Unauthorized port scanning may be illegal in your jurisdiction.
