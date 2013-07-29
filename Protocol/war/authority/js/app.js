var directory = {

    views: {},

    models: {},

    loadTemplates: function(views, callback) {

        var deferreds = [];

        $.each(views, function(index, view) {
            if (directory[view]) {
                deferreds.push($.get('tpl/' + view + '.html', function(data) {
                    directory[view].prototype.template = _.template(data);
                }, 'html'));
            } else {
                alert(view + " not found");
            }
        });

        $.when.apply(null, deferreds).done(callback);
    }

};

directory.Router = Backbone.Router.extend({

    routes: {
        "":                 "certify",
        "certify":          "certify",
        "verify":          "verify",
        "verify/:hash":     "verifyHash",
        "products/:id":    "productDetails",
        "newProduct":		"newProduct",
        "*path":			"home"
    },

    initialize: function () {
    	_.bindAll(this);
        directory.shellView = new directory.ShellView();
        $('body').html(directory.shellView.render().el);
    	this.searchResults = new directory.ProductCollection();
    	this.searchResults.fetch();

        directory.shellView.selectMenuItem('authority-menu');
    	this.$content = $("#content");
    },

    home: function () {

    },

    certify: function () {
		if (!directory.certifyView) {
			directory.certifyView = new directory.CertifyView({searchResults : this.searchResults});
	    }
		directory.certifyView.render();
    },
    
    verify: function () {
		if (!directory.verifyView) {
			directory.verifyView = new directory.VerifyView({searchResults : this.searchResults});
	    }
		directory.verifyView.render();
    },

    verifyHash: function(hash){
    	this.verify();
    	directory.verifyView.doVerification(hash);
    },
    
    productDetails: function (id) {
    	if(directory.certifyView == null)
    		this.certify();
    	
    		directory.certifyView.renderProduct(id);
    },
    
    newProduct: function () {
    	if(directory.certifyView == null)
    		this.certify();
    	else
    		directory.certifyView.newProduct();
    }

});

$(document).on("ready", function () {
    directory.loadTemplates(["CertifyView", "VerifyView", "ContactView", "ShellView", "ProductListView", "ProductView",
                             "CertificateSummaryView", "ProductSummaryView", "ProductListItemView"],
        function () {
            directory.router = new directory.Router();
            Backbone.history.start();
        });
});
