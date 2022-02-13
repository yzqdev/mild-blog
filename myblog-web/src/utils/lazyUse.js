import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
export function useMarkdown(app){



    app.use(MdEditor);
}
