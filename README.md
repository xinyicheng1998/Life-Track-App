# Life-Track-App

LifeTrack360 is a comprehensive lifestyle tracking app that enables users to monitor their calorie intake, fitness progress, and location insights on website.

With personalized profile management, daily step count monitoring, and weekly location analysis, users can make informed decisions about their diet, exercise routines, and travel plans.

The app is securely deployed on AWS, ensuring a seamless user experience through any web browser. The development team utilizes Github Actions to ensure consistent testing, and building of code changes.

## Repository Structure

This repository contains the source code for the LifeTrack360 app, organized into the following folders:

- `frontend`: Contains the source code for the web-based user interface, including HTML, CSS, and JavaScript files.
- `backend`: Contains the source code for the server-side logic, including database access and API implementation.
- `.github`: Contains the configuration files for the continuous integration (CI) pipeline using Github Actions.
- `documentation`: Contains the user stories and other project documentation.

## Getting Started

To set up the development environment for LifeTrack360, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/xinyicheng1998/Life-Track-App.git
   cd Life-Track-App
   ```

2. Install dependencies for the frontend:

   ```
   cd frontend
   npm install
   ```

3. Install dependencies for the backend:

   ```
   cd backend
   npm install
   ```

4. Create a `.env` file in the `backend` folder with the following environment variables:

   ```
   DATABASE_URL=<your_database_url>
   JWT_SECRET=<your_jwt_secret>
   ```

5. Start the development servers:

   - Frontend:

     ```
     cd frontend
     npm start
     ```

   - Backend:

     ```
     cd backend
     npm start
     ```

Now, you should be able to access the app at `http://localhost:3000` in your web browser.

## Contributing

We welcome contributions from the community. To contribute, please follow these steps:

1. Fork the repository and create a new branch with a descriptive name.
2. Make your changes in the new branch.
3. Commit your changes and push them to your fork.
4. Create a pull request from your branch to the main repository.

Please ensure that your code follows best practices, and include any necessary tests or documentation updates.

## License

This project is licensed under the MIT License. For more information, see the [LICENSE](LICENSE) file.
