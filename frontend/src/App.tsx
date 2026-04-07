import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Layout } from "./components";
import EvaluateTransaction from "./pages/EvaluateTransaction";
import RecentEvaluations from "./pages/RecentEvaluations";
import Config from "./pages/Config";
import AlertsPage from "./pages/AlertsPage";
import { ROUTES } from "./utils";

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<EvaluateTransaction />} />
          <Route path={ROUTES.EVALUATE} element={<EvaluateTransaction />} />
          <Route path={ROUTES.HISTORY} element={<RecentEvaluations />} />
          <Route path={ROUTES.CONFIG} element={<Config />} />
          <Route path={ROUTES.ALERTS} element={<AlertsPage />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
