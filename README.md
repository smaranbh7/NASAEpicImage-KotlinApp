# NASA EPIC Image Viewer

This app fetches images of Earth taken by NASA's EPIC camera onboard the NOAA DSCOVR spacecraft. It displays not only the image but also important information regarding the positions of Earth, the Moon, and the Sun at the time the image was captured.

## Features
- Fetches images from NASA's EPIC camera
- Displays image capture date and time
- Shows the coordinates where the image was taken on Earth
- Provides positions of:
  - **DSCOVR spacecraft** (the source of the image)
  - **Moon** 
  - **Sun**
  
## Data Provided
For each image, the app shows:
- **DSCOVR Position**: Location of the DSCOVR spacecraft in space (X, Y, Z coordinates)
- **Lunar Position**: Location of the Moon in space at the time of the image (X, Y, Z coordinates)
- **Sun Position**: Location of the Sun in space at the time of the image (X, Y, Z coordinates)
- **Earth Coordinates**: Latitude and Longitude of the Earth as seen in the image
- **Timestamp**: Date and time when the image was taken

## Setup and Installation

1. Clone the repository:
git clone https://github.com/yourusername/nasa-epic-image-viewer.git


2. Install necessary dependencies:
- If using Node.js, run:
  ```
  npm install
  ```

3. Run the app:
- If using React, run:
  ```
  npm start
  ```

## Technologies Used
- [NASA EPIC API](https://epic.gsfc.nasa.gov/) for fetching images
