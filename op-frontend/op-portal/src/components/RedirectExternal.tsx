import React, {useEffect} from "react";

type RedirectExternalProps = {
    url: string;
};

const RedirectExternal = (props: RedirectExternalProps) => {
    const {url} = props;

    useEffect(() => {
        window.open(url);
    }, []);

    return <section>已跳转页面 ...</section>;
};

export default RedirectExternal;
