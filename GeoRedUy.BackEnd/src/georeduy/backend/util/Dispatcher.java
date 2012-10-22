/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package georeduy.backend.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class CharResponseWrapper extends HttpServletResponseWrapper {
    protected CharArrayWriter _charWriter;
    protected PrintWriter _writer;
    protected boolean _getOutputStreamCalled;
    protected boolean _getWriterCalled;

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);

        _charWriter = new CharArrayWriter();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (_getWriterCalled) {
            throw new IllegalStateException("getWriter already called");
        }

        _getOutputStreamCalled = true;
        return super.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (_writer != null) {
            return _writer;
        }
        if (_getOutputStreamCalled) {
            throw new IllegalStateException("getOutputStream already called");
        }
        _getWriterCalled = true;
        _writer = new PrintWriter(_charWriter);
        return _writer;
    }

    @Override
    public String toString() {
        String s = null;

        if (_writer != null) {
            s = _charWriter.toString();
        }
        return s;
    }

}

public class Dispatcher {
    private HttpServletResponse _response;
    private HttpServletRequest _request;

    public Dispatcher (HttpServletRequest request, HttpServletResponse response) {
        _request = request;
        _response = response;
    }

    public static String fetch(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CharResponseWrapper resWrapper = new CharResponseWrapper(response);

        request.getRequestDispatcher("/"+path).include(request, resWrapper);
        request.getRequestDispatcher("/WEB-INF/"+path+".jsp").include(request, resWrapper);

        return resWrapper.toString();
    }

    public String fetch(String path) throws ServletException, IOException {
        CharResponseWrapper resWrapper = new CharResponseWrapper(_response);

        _request.getRequestDispatcher("/"+path).include(_request, resWrapper);
        _request.getRequestDispatcher("/WEB-INF/"+path+".jsp").include(_request, resWrapper);

        return resWrapper.toString();
    }
}
