import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Layout } from "./components";
import EvaluateTransaction from "./pages/EvaluateTransaction";
import RecentEvaluations from "./pages/RecentEvaluations";
import { ROUTES } from "./utils";

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path={ROUTES.EVALUATE} element={<EvaluateTransaction />} />
          <Route path={ROUTES.HISTORY} element={<RecentEvaluations />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
