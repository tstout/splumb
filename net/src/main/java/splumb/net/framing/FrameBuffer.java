package splumb.net.framing;

import java.nio.ByteBuffer;

class FrameBuffer {
    private ByteBuffer buff;
    private int targetLength;
    private int currentLength;

    FrameBuffer(int maxFrameLength) {
        buff = ByteBuffer.allocate(maxFrameLength);
    }

    int remainingBytes() {
        return targetLength - currentLength;
    }

    void setTargetLength(int targetLength) {
        this.targetLength = targetLength;
    }

    void reset(int newTargetLength) {
        currentLength = 0;
        targetLength = newTargetLength;
    }

    int append(ByteBuffer buff) {
        this.buff.put(buff);
        return 0;
    }

}
//MsgBuff(int initSize)
//        {
//        _data = new Byte[initSize];
//_maxLen = initSize;
//_currentLen = 0;
//}
//
//public void Reset(int newTargetLen)
//        {
//        _currentLen = 0;
//_targetLen = newTargetLen;
//}
//
//public MsgBuff SetTargetLength(int len)
//        {
//        _targetLen = len;
//return this;
//}
//
//public int TargetLength
//        {
//        get { return _targetLen; }
//        }
//
//public byte[] RawBuff
//        {
//        get { return _data; }
//        }
//
//public int RemainingBytes
//        {
//        get { return _targetLen - _currentLen; }
//        }
//
//public int Append(Socket sock)
//        {
//        int bytesRead = sock.Receive(
//        _data,
//        _currentLen,
//        RemainingBytes, SocketFlags.None);
//
//_currentLen += bytesRead;
//return bytesRead;
//}
//
//private byte[] _data;
//private int _targetLen;
//private int _currentLen;
//private int _maxLen;
//}